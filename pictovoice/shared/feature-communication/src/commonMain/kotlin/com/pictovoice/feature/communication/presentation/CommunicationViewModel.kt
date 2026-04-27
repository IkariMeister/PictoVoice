package com.pictovoice.feature.communication.presentation

import com.pictovoice.feature.communication.domain.addPictogram
import com.pictovoice.feature.communication.domain.clearSentence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CommunicationViewModel(initialState: CommunicationUiState = CommunicationUiState()) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<CommunicationUiState> = _state.asStateFlow()

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
