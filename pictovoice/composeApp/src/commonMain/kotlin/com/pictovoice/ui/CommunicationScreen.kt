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
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationViewModel

@Composable
fun CommunicationScreen(
    viewModel: CommunicationViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

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
            onPictogramSelected = { viewModel.onEvent(CommunicationEvent.SelectPictogram(it)) },
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpeakButton(
            enabled = state.sentence.items.isNotEmpty() && !state.isSpeaking,
            isSpeaking = state.isSpeaking,
            onClick = { viewModel.onEvent(CommunicationEvent.SpeakTapped) },
        )
    }
}
