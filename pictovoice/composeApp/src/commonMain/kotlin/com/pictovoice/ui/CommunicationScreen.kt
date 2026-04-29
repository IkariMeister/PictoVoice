package com.pictovoice.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram
import com.pictovoice.feature.communication.presentation.CommunicationUiState

@Composable
fun CommunicationScreen(
    state: CommunicationUiState,
    statusMessage: String?,
    onSelectPictogram: (Pictogram) -> Unit,
    onSpeakSentence: () -> Unit,
    onClearSentence: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().semantics { contentDescription = "Communication screen" },
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        statusMessage?.let {
            Text(
                text = it,
                modifier = Modifier.semantics { contentDescription = "Status $it" },
            )
        }
        SentenceBuilderBar(items = state.sentence.items)
        PictogramGrid(
            pictograms = state.pictograms,
            onSelectPictogram = onSelectPictogram,
            modifier = Modifier.weight(1f),
        )
        SpeakButton(
            enabled = state.sentence.items.isNotEmpty(),
            onSpeak = onSpeakSentence,
        )
        Button(
            onClick = onClearSentence,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .semantics { contentDescription = "Clear sentence" },
        ) {
            Text("Clear sentence")
        }
    }
}
