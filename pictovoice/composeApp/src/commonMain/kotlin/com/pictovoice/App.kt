package com.pictovoice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationEffect
import com.pictovoice.feature.communication.presentation.CommunicationEvent
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import com.pictovoice.ui.CommunicationScreen

@Composable
fun App(onSpeakSentence: (String) -> Unit = {}) {
    val viewModel = remember(onSpeakSentence) { CommunicationViewModel(onSpeakSentence = onSpeakSentence) }
    val state by viewModel.state.collectAsState()
    var statusMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is CommunicationEffect.StatusMessage -> statusMessage = effect.value
            }
        }
    }

    PictoVoiceTheme {
        CommunicationScreen(
            state = state,
            statusMessage = statusMessage,
            onSelectPictogram = { viewModel.onEvent(CommunicationEvent.SelectPictogram(it)) },
            onSpeakSentence = { viewModel.onEvent(CommunicationEvent.SpeakSentence) },
            onClearSentence = { viewModel.onEvent(CommunicationEvent.ClearSentence) },
            modifier = Modifier.fillMaxSize().padding(16.dp),
        )
    }
}
