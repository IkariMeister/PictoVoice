package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.telemetry.NoopTelemetry
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.communication.domain.NoopTextToSpeechEngine
import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import com.pictovoice.feature.communication.domain.addPictogram
import com.pictovoice.feature.communication.domain.clearSentence
import com.pictovoice.feature.communication.domain.speakSentence
import com.pictovoice.feature.vocabulary.data.InMemoryVocabularyRepository
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CommunicationViewModel(
    private val vocabularyRepository: VocabularyRepository = InMemoryVocabularyRepository(),
    private val textToSpeechEngine: TextToSpeechEngine = NoopTextToSpeechEngine,
    private val telemetry: Telemetry = NoopTelemetry,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state = MutableStateFlow(CommunicationUiState())
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()

    private val _effects = Channel<CommunicationEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        scope.launch {
            _state.value = _state.value.copy(pictograms = vocabularyRepository.listPictograms())
        }
    }

    fun onEvent(event: CommunicationEvent) {
        _state.value =
            when (event) {
                is CommunicationEvent.SelectPictogram -> {
                    telemetry.event("pictogram_selected", mapOf("id" to event.pictogram.id))
                    _state.value.copy(sentence = addPictogram(_state.value.sentence, event.pictogram))
                }
                CommunicationEvent.ClearSentence ->
                    _state.value.copy(sentence = clearSentence())
                CommunicationEvent.SpeakTapped -> {
                    scope.launch {
                        val sentence = _state.value.sentence
                        if (sentence.items.isEmpty()) {
                            _effects.send(CommunicationEffect.EmptySentenceIgnored)
                            return@launch
                        }
                        _state.value = _state.value.copy(isSpeaking = true)
                        try {
                            speakSentence(sentence, vocabularyRepository, textToSpeechEngine)
                        } finally {
                            _state.value = _state.value.copy(isSpeaking = false)
                        }
                    }
                    _state.value
                }
            }
    }
}
