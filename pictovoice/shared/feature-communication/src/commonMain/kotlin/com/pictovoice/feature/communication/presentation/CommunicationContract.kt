package com.pictovoice.feature.communication.presentation

import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.model.Sentence

data class CommunicationUiState(
    val pictograms: List<Pictogram> = emptyList(),
    val sentence: Sentence = Sentence(emptyList()),
    val isSpeaking: Boolean = false,
)

sealed interface CommunicationEvent {
    data class SelectPictogram(val pictogram: Pictogram) : CommunicationEvent
    data class RemovePictogramAt(val index: Int) : CommunicationEvent
    data object ClearSentence : CommunicationEvent
    data object SpeakTapped : CommunicationEvent
    data object SyncRequested : CommunicationEvent
}

sealed interface CommunicationEffect {
    data object EmptySentenceIgnored : CommunicationEffect
    data object SyncSkippedOffline : CommunicationEffect
    data object SyncCompleted : CommunicationEffect
}
