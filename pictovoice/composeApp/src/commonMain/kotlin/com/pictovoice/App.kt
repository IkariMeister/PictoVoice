package com.pictovoice

import androidx.compose.runtime.Composable
import com.pictovoice.core.ui.PictoVoiceTheme
import com.pictovoice.feature.communication.presentation.CommunicationViewModel
import com.pictovoice.ui.CommunicationScreen

@Composable
fun App(viewModel: CommunicationViewModel = CommunicationViewModel()) {
    PictoVoiceTheme {
        CommunicationScreen(viewModel = viewModel)
    }
}
