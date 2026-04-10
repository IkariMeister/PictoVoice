package com.pictovoice

import androidx.compose.runtime.Composable
import com.pictovoice.presentation.communication.CommunicationViewModel
import com.pictovoice.theme.PictoVoiceTheme
import com.pictovoice.ui.CommunicationScreen

@Composable
fun App(viewModel: CommunicationViewModel) {
    PictoVoiceTheme {
        CommunicationScreen(viewModel = viewModel)
    }
}
