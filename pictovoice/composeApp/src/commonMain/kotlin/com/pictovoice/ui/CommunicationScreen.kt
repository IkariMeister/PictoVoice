package com.pictovoice.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationEffect
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationUiState
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import kotlinx.coroutines.flow.onEach

internal fun feedbackMessageFor(effect: CommunicationEffect): String =
    when (effect) {
        CommunicationEffect.EmptySentenceIgnored -> "Sentence is empty"
        CommunicationEffect.SyncSkippedOffline -> "Offline: sync skipped"
        CommunicationEffect.SyncCompleted -> "Synced successfully"
    }

@Composable
fun CommunicationScreen(
    viewModel: CommunicationViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    var feedbackMessage by remember { mutableStateOf<String?>(null) }
    val effect by viewModel.effects.onEach { effect ->
        feedbackMessage = feedbackMessageFor(effect)
    }.collectAsState(initial = null)

    if (effect != null) {
        // Collected for side effect into feedbackMessage; value is represented in UI text.
    }

    CommunicationScreenContent(
        state = state,
        feedbackMessage = feedbackMessage,
        onPictogramSelected = { viewModel.onEvent(CommunicationEvent.SelectPictogram(it)) },
        onSentencePictogramTapped = { viewModel.onEvent(CommunicationEvent.RemovePictogramAt(it)) },
        onSpeakTapped = { viewModel.onEvent(CommunicationEvent.SpeakTapped) },
        onClearTapped = { viewModel.onEvent(CommunicationEvent.ClearSentence) },
        onSyncRequested = { viewModel.onEvent(CommunicationEvent.SyncRequested) },
        onDismissFeedback = { feedbackMessage = null },
        modifier = modifier,
    )
}

@Composable
private fun CommunicationScreenContent(
    state: CommunicationUiState,
    feedbackMessage: String?,
    onPictogramSelected: (Pictogram) -> Unit,
    onSentencePictogramTapped: (Int) -> Unit,
    onSpeakTapped: () -> Unit,
    onClearTapped: () -> Unit,
    onSyncRequested: () -> Unit,
    onDismissFeedback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        if (feedbackMessage != null) {
            Button(onClick = onDismissFeedback) {
                Text(feedbackMessage)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        SentenceBuilderBar(
            sentencePictograms = state.sentence.items,
            onPictogramTapped = onSentencePictogramTapped,
        )
        Spacer(modifier = Modifier.height(16.dp))

        PictogramGrid(
            pictograms = state.pictograms,
            onPictogramSelected = onPictogramSelected,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpeakButton(
            enabled = state.sentence.items.isNotEmpty() && !state.isSpeaking,
            isSpeaking = state.isSpeaking,
            onClick = onSpeakTapped,
        )

        Spacer(modifier = Modifier.height(8.dp))

        ClearSentenceButton(
            enabled = state.sentence.items.isNotEmpty(),
            onClick = onClearTapped,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onSyncRequested,
        ) {
            Text("Sync")
        }
    }
}

@Composable
private fun CommunicationScreenPreview() {
    PictoVoiceTheme {
        CommunicationScreenContent(
            state =
                CommunicationUiState(
                    pictograms =
                        listOf(
                            Pictogram("yes", "Yes", "Yes"),
                            Pictogram("no", "No", "No"),
                            Pictogram("water", "Water", "Water"),
                            Pictogram("help", "Help", "Help"),
                        ),
                    sentence =
                        com.pictovoice.core.model.Sentence(
                            items =
                                listOf(
                                    Pictogram("yes", "Yes", "Yes"),
                                    Pictogram("water", "Water", "Water"),
                                ),
                        ),
                ),
            onPictogramSelected = {},
            onSentencePictogramTapped = {},
            onSpeakTapped = {},
            onClearTapped = {},
            onSyncRequested = {},
            feedbackMessage = "Offline: sync skipped",
            onDismissFeedback = {},
        )
    }
}
