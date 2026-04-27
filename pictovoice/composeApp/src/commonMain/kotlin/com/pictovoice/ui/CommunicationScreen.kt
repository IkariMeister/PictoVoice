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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationUiState
import com.pictovoice.feature.communication.presentation.CommunicationViewModel

@Composable
fun CommunicationScreen(
    viewModel: CommunicationViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    CommunicationScreenContent(
        state = state,
        onPictogramSelected = { viewModel.onEvent(CommunicationEvent.SelectPictogram(it)) },
        onSpeakTapped = { viewModel.onEvent(CommunicationEvent.SpeakTapped) },
        onClearTapped = { viewModel.onEvent(CommunicationEvent.ClearSentence) },
        onSyncRequested = { viewModel.onEvent(CommunicationEvent.SyncRequested) },
        modifier = modifier,
    )
}

@Composable
private fun CommunicationScreenContent(
    state: CommunicationUiState,
    onPictogramSelected: (Pictogram) -> Unit,
    onSpeakTapped: () -> Unit,
    onClearTapped: () -> Unit,
    onSyncRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        SentenceBuilderBar(sentencePictograms = state.sentence.items)
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

        Button(
            enabled = state.sentence.items.isNotEmpty(),
            onClick = onClearTapped,
        ) {
            Text("Clear")
        }

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
            onSpeakTapped = {},
            onClearTapped = {},
            onSyncRequested = {},
        )
    }
}
