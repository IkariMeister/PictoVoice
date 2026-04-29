package com.pictovoice.feature.communication.presentation

import com.pictovoice.feature.communication.domain.addPictogram
import com.pictovoice.feature.communication.domain.clearSentence
import com.pictovoice.feature.vocabulary.data.InMemoryVocabularyRepository
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunicationViewModel(
    private val vocabularyRepository: VocabularyRepository = InMemoryVocabularyRepository(),
    private val onSpeakSentence: (String) -> Unit = {},
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    initialState: CommunicationUiState = CommunicationUiState(),
) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()
    private val _effects = MutableSharedFlow<CommunicationEffect>(extraBufferCapacity = 8)
    val effects: SharedFlow<CommunicationEffect> = _effects.asSharedFlow()

    init {
        coroutineScope.launch {
            val pictograms = vocabularyRepository.listPictograms()
            _state.value = _state.value.copy(pictograms = pictograms)
        }
    }

    fun onEvent(event: CommunicationEvent) {
        _state.value =
            when (event) {
                is CommunicationEvent.SelectPictogram ->
                    _state.value.copy(sentence = addPictogram(_state.value.sentence, event.pictogram))
                CommunicationEvent.SpeakSentence -> {
                    val sentence = _state.value.sentence.items.joinToString(" ") { it.spokenText }.trim()
                    if (sentence.isEmpty()) {
                        _effects.tryEmit(CommunicationEffect.StatusMessage("Sentence is empty"))
                    } else {
                        try {
                            onSpeakSentence(sentence)
                            _effects.tryEmit(CommunicationEffect.StatusMessage("Speaking: $sentence"))
                        } catch (_: Throwable) {
                            _effects.tryEmit(CommunicationEffect.StatusMessage("Unable to speak sentence"))
                        }
                    }
                    _state.value
                }
                CommunicationEvent.ClearSentence ->
                    _state.value.copy(sentence = clearSentence())
            }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}
