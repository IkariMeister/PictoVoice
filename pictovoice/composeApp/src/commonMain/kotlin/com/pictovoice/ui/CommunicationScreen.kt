package com.pictovoice.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.domain.model.Pictogram
import com.pictovoice.domain.model.Sentence
import com.pictovoice.presentation.communication.CommunicationEvent
import com.pictovoice.presentation.communication.CommunicationViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CommunicationScreen(
    viewModel: CommunicationViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val sentencePictograms =
        state.sentence.items.mapNotNull { id -> state.pictograms.find { it.id == id } }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        SentenceBuilderBar(sentencePictograms = sentencePictograms)
        Spacer(modifier = Modifier.height(16.dp))
        PictogramGrid(
            pictograms = state.pictograms,
            onPictogramClick = { viewModel.onEvent(CommunicationEvent.PictogramTapped(it)) },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.height(16.dp))
        SpeakButton(
            enabled = state.sentence.items.isNotEmpty() && !state.isSpeaking,
            onClick = { viewModel.onEvent(CommunicationEvent.SpeakTapped) },
        )
    }
}

@Preview
@Composable
fun CommunicationScreenPreview() {
    val pictograms =
        listOf(
            Pictogram("1", "Yes", "Yes", null, null, 0),
            Pictogram("2", "No", "No", null, null, 1),
            Pictogram("3", "Water", "Water", null, null, 2),
            Pictogram("4", "Help", "Help", null, null, 3),
        )
    // Mocking ViewModel for preview is complex in KMP without a proper interface or mock engine.
    // For now, we just wrap the UI components in a similar layout for visual check.
    androidx.compose.material3.MaterialTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            SentenceBuilderBar(
                sentencePictograms = listOf(pictograms[0], pictograms[2]),
            )
            Spacer(modifier = Modifier.height(16.dp))
            PictogramGrid(
                pictograms = pictograms,
                onPictogramClick = {},
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.height(16.dp))
            SpeakButton(
                enabled = true,
                onClick = {},
            )
        }
    }
}
