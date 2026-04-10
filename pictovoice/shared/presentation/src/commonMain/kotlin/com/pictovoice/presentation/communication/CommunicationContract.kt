package com.pictovoice.presentation.communication

import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.model.Sentence

data class CommunicationUiState(
    val pictograms: List<Pictogram>,
    val sentence: Sentence,
    val isSpeaking: Boolean = false,
)

sealed interface CommunicationEvent {
    data class PictogramTapped(val id: String) : CommunicationEvent
    data object SpeakTapped : CommunicationEvent
    data object ClearTapped : CommunicationEvent
}

sealed interface CommunicationEffect {
    data object SpeakIgnoredEmpty : CommunicationEffect
}
