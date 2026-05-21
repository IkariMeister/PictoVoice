package com.pictovoice.feature.communication.presentation

import com.pictovoice.feature.communication.domain.addPictogram
import com.pictovoice.feature.communication.domain.clearSentence
import com.pictovoice.feature.vocabulary.data.InMemoryVocabularyRepository
import com.pictovoice.feature.vocabulary.domain.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommunicationViewModel(
    initialState: CommunicationUiState = CommunicationUiState(),
    private val vocabularyRepository: VocabularyRepository,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()

    init {
        scope.launch {
            _state.value = _state.value.copy(pictograms = vocabularyRepository.listPictograms())
        }
    }

    constructor(
        initialState: CommunicationUiState = CommunicationUiState(),
    ) : this(
        initialState = initialState,
        vocabularyRepository = InMemoryVocabularyRepository(),
    )

    fun onEvent(event: CommunicationEvent) {
        _state.value =
            when (event) {
                is CommunicationEvent.SelectPictogram ->
                    _state.value.copy(sentence = addPictogram(_state.value.sentence, event.pictogram))
                CommunicationEvent.ClearSentence ->
                    _state.value.copy(sentence = clearSentence())
            }
    }
}
