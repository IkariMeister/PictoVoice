package com.pictovoice.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PictoVoiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme =
            darkColorScheme(
                background = Color(0xFF000000),
                surface = Color(0xFF121212),
                onSurface = Color(0xFFFFFFFF),
                primary = Color(0xFFFFEB3B),
                onPrimary = Color(0xFF000000),
            ),
        content = content,
    )
}
