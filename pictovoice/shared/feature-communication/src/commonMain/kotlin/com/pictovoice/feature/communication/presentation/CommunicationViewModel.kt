package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.telemetry.NoopTelemetry
import com.pictovoice.core.telemetry.Telemetry
import com.pictovoice.feature.communication.domain.NoopTextToSpeechEngine
import com.pictovoice.feature.communication.domain.OnDevicePredictionEngine
import com.pictovoice.feature.communication.domain.TextToSpeechEngine
import com.pictovoice.feature.communication.domain.addPictogram
import com.pictovoice.feature.communication.domain.clearSentence
import com.pictovoice.feature.communication.domain.removePictogramAt
import com.pictovoice.feature.communication.domain.speakSentence
import com.pictovoice.feature.vocabulary.data.InMemoryVocabularyRepository
import com.pictovoice.feature.vocabulary.data.network.NetworkMonitor
import com.pictovoice.feature.vocabulary.data.network.OfflineNetworkMonitor
import com.pictovoice.feature.vocabulary.domain.SyncResult
import com.pictovoice.feature.vocabulary.domain.SyncVocabularyHandler
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlinx.coroutines.CoroutineDispatcher
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
    private val predictionEngine: OnDevicePredictionEngine = OnDevicePredictionEngine(),
    networkMonitor: NetworkMonitor = OfflineNetworkMonitor,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)
    private val syncVocabularyHandler = SyncVocabularyHandler(networkMonitor)

    private val _state = MutableStateFlow(CommunicationUiState())
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()

    private val _effects = Channel<CommunicationEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        scope.launch {
            refreshVocabulary()
        }
    }

    fun onEvent(event: CommunicationEvent) {
        _state.value =
            when (event) {
                is CommunicationEvent.SelectPictogram -> {
                    telemetry.event("pictogram_selected", mapOf("id" to event.pictogram.id))
                    _state.value.copy(
                        sentence = addPictogram(_state.value.sentence, event.pictogram),
                        predictions = predictionEngine(
                            addPictogram(_state.value.sentence, event.pictogram),
                            _state.value.pictograms,
                        ),
                    )
                }
                is CommunicationEvent.RemovePictogramAt ->
                    _state.value.copy(
                        sentence = removePictogramAt(_state.value.sentence, event.index),
                        predictions = predictionEngine(
                            removePictogramAt(_state.value.sentence, event.index),
                            _state.value.pictograms,
                        ),
                    )
                CommunicationEvent.ClearSentence ->
                    _state.value.copy(
                        sentence = clearSentence(),
                        predictions = predictionEngine(clearSentence(), _state.value.pictograms),
                    )
                CommunicationEvent.SpeakTapped -> {
                    scope.launch {
                        val sentence = _state.value.sentence
                        if (sentence.items.isEmpty()) {
                            _effects.send(CommunicationEffect.EmptySentenceIgnored)
                            return@launch
                        }
                        _state.value = _state.value.copy(isSpeaking = true)
                        try {
                            // Speech path remains local-first and independent from network.
                            speakSentence(sentence, vocabularyRepository, textToSpeechEngine)
                        } finally {
                            _state.value = _state.value.copy(isSpeaking = false)
                        }
                    }
                    _state.value
                }
                CommunicationEvent.SyncRequested -> {
                    scope.launch {
                        when (syncVocabularyHandler { refreshPictograms() }) {
                            SyncResult.SkippedOffline -> _effects.send(CommunicationEffect.SyncSkippedOffline)
                            SyncResult.Synced -> _effects.send(CommunicationEffect.SyncCompleted)
                        }
                    }
                    _state.value
                }
            }
    }

    private suspend fun refreshPictograms() {
        refreshVocabulary()
    }

    private suspend fun refreshVocabulary() {
        val pictograms = vocabularyRepository.listPictograms()
        _state.value =
            _state.value.copy(
                pictograms = pictograms,
                predictions = predictionEngine(_state.value.sentence, pictograms),
            )
    }
}
