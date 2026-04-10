package com.pictovoice.presentation.communication

import com.pictovoice.domain.command.DomainCommand
import com.pictovoice.domain.dispatcher.CommandHandler
import com.pictovoice.domain.model.Sentence
import com.pictovoice.domain.repo.VocabularyRepository
import com.pictovoice.presentation.telemetry.SentryBreadcrumbs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommunicationViewModel(
    private val commandHandler: CommandHandler,
    private val vocabularyRepository: VocabularyRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _state = MutableStateFlow(
        CommunicationUiState(
            pictograms = emptyList(),
            sentence = Sentence(emptyList()),
        ),
    )
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()

    private val _effects = Channel<CommunicationEffect>(capacity = Channel.BUFFERED)
    val effects: Flow<CommunicationEffect> = _effects.receiveAsFlow()

    init {
        scope.launch {
            val grid = vocabularyRepository.listPictogramsForGrid()
            _state.value = _state.value.copy(pictograms = grid)
        }
    }

    fun onEvent(event: CommunicationEvent) {
        when (event) {
            is CommunicationEvent.PictogramTapped -> {
                SentryBreadcrumbs.record("pictogram_selected", mapOf("id" to event.id))
                scope.launch {
                    val next = commandHandler.handle(DomainCommand.AddPictogram(event.id), _state.value.sentence)
                    _state.value = _state.value.copy(sentence = next)
                }
            }
            CommunicationEvent.SpeakTapped -> {
                scope.launch {
                    val sentence = _state.value.sentence
                    if (sentence.items.isEmpty()) {
                        _effects.send(CommunicationEffect.SpeakIgnoredEmpty)
                        return@launch
                    }
                    _state.value = _state.value.copy(isSpeaking = true)
                    try {
                        withContext(Dispatchers.Default) {
                            commandHandler.handle(DomainCommand.SpeakSentence, sentence)
                        }
                    } finally {
                        _state.value = _state.value.copy(isSpeaking = false)
                    }
                }
            }
            CommunicationEvent.ClearTapped -> {
                scope.launch {
                    val next = commandHandler.handle(DomainCommand.ClearSentence, _state.value.sentence)
                    _state.value = _state.value.copy(sentence = next)
                }
            }
        }
    }
}
