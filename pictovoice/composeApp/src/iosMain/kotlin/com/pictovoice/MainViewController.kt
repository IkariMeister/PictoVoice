package com.pictovoice

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun makeMainViewController(): UIViewController =
    ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        }
    ) {
        App()
    }
