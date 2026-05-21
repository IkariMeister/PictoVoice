package com.pictovoice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import com.pictovoice.ui.CommunicationScreen

@Composable
fun App(
    viewModel: CommunicationViewModel = CommunicationViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    PictoVoiceTheme {
        CommunicationScreen(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = modifier,
        )
    }
}
