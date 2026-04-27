package com.pictovoice.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.ui.PictoVoiceTheme

@Composable
fun SpeakButton(
    enabled: Boolean,
    isSpeaking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = 56.dp),
    ) {
        Text(
            text = if (isSpeaking) "Speaking..." else "Speak",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun SpeakButtonEnabledPreview() {
    PictoVoiceTheme {
        SpeakButton(enabled = true, isSpeaking = false, onClick = {})
    }
}

@Composable
private fun SpeakButtonDisabledPreview() {
    PictoVoiceTheme {
        SpeakButton(enabled = false, isSpeaking = false, onClick = {})
    }
}

@Composable
private fun SpeakButtonSpeakingPreview() {
    PictoVoiceTheme {
        SpeakButton(enabled = false, isSpeaking = true, onClick = {})
    }
}
