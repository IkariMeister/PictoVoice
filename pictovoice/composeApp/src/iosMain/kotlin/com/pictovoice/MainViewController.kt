package com.pictovoice

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun makeMainViewController(): UIViewController =
    ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        }
    ) {
        App(viewModel = com.pictovoice.feature.communication.presentation.CommunicationViewModel(
            textToSpeechEngine = IosTextToSpeechEngine(),
        ))
    }
