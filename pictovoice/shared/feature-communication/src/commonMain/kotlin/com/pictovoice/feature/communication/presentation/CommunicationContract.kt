package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence

data class CommunicationUiState(
    val pictograms: List<Pictogram> = emptyList(),
    val sentence: Sentence = Sentence(emptyList()),
)

sealed interface CommunicationEvent {
    data class SelectPictogram(val pictogram: Pictogram) : CommunicationEvent
    data object SpeakSentence : CommunicationEvent
    data object ClearSentence : CommunicationEvent
}

sealed interface CommunicationEffect {
    data class StatusMessage(val value: String) : CommunicationEffect
}
