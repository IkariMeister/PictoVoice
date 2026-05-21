package com.pictovoice

import androidx.compose.ui.window.ComposeUIViewController

fun makeMainViewController() =
    ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
        },
    ) {
        App()
    }
