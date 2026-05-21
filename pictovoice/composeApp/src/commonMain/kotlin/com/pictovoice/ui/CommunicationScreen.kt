package com.pictovoice.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationUiState

@Composable
fun CommunicationScreen(
    state: CommunicationUiState,
    onEvent: (CommunicationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sentenceText =
        state.sentence.items
            .takeIf { it.isNotEmpty() }
            ?.joinToString(" ") { it.label }
            ?: "No words selected yet"

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Build your sentence",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
            )
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                shape = MaterialTheme.shapes.large,
            ) {
                Text(
                    text = sentenceText,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
                )
            }
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.72f)
                        .heightIn(min = 240.dp),
            ) {
                PictogramGrid(
                    pictograms = state.pictograms,
                    onPictogramSelected = { pictogram ->
                        onEvent(CommunicationEvent.SelectPictogram(pictogram))
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            Button(
                onClick = { onEvent(CommunicationEvent.ClearSentence) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Clear sentence")
            }
        }
    }
}
