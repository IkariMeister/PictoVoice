package com.pictovoice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationViewModel

@Composable
fun App(viewModel: CommunicationViewModel = CommunicationViewModel()) {
    val state by viewModel.state.collectAsState()
    PictoVoiceTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            Text("Sentence: ${state.sentence.items.joinToString(" ") { it.label }}")
            state.pictograms.forEach { pictogram ->
                Button(
                    onClick = { viewModel.onEvent(CommunicationEvent.SelectPictogram(pictogram)) },
                ) {
                    Text("Add ${pictogram.label}")
                }
            }
            Button(onClick = { viewModel.onEvent(CommunicationEvent.SpeakTapped) }) {
                Text(if (state.isSpeaking) "Speaking..." else "Speak")
            }
            Button(onClick = { viewModel.onEvent(CommunicationEvent.ClearSentence) }) {
                Text("Clear")
            }
        }
    }
}
