package com.pictovoice.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object PictoVoiceColors {
    val Background = Color(0xFF000000)
    val Surface = Color(0xFF121212)
    val OnSurface = Color(0xFFFFFFFF)
    val Primary = Color(0xFFFFEB3B)
    val OnPrimary = Color(0xFF000000)
    val Secondary = Color(0xFF64B5F6)
    val Outline = Color(0xFFB0BEC5)
}

fun pictoVoiceDarkScheme(): ColorScheme =
    darkColorScheme(
        primary = PictoVoiceColors.Primary,
        onPrimary = PictoVoiceColors.OnPrimary,
        secondary = PictoVoiceColors.Secondary,
        background = PictoVoiceColors.Background,
        surface = PictoVoiceColors.Surface,
        onSurface = PictoVoiceColors.OnSurface,
        outline = PictoVoiceColors.Outline,
    )

val PictoVoiceTypography: Typography
    get() =
        Typography(
            bodyLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
            titleLarge = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                lineHeight = 34.sp,
            ),
        )

@Composable
fun PictoVoiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = pictoVoiceDarkScheme(),
        typography = PictoVoiceTypography,
        content = content,
    )
}
