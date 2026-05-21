package com.pictovoice.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun PictoVoiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme =
            lightColorScheme(
                background = Color(0xFFF7F4ED),
                surface = Color(0xFFFFFFFF),
                onSurface = Color(0xFF1F1F1C),
                onBackground = Color(0xFF1F1F1C),
                primary = Color(0xFF135C63),
                onPrimary = Color(0xFFFFFFFF),
                secondary = Color(0xFFE2A400),
                onSecondary = Color(0xFF1F1F1C),
            ),
        typography =
            Typography(
                headlineSmall =
                    TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                titleLarge =
                    TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                titleMedium =
                    TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                bodyLarge =
                    TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                    ),
                labelLarge =
                    TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            ),
        content = content,
    )
}
