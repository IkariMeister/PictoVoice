package com.pictovoice.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressedContainerColor = MaterialTheme.colorScheme.primaryContainer
    val pressedContentColor = MaterialTheme.colorScheme.onPrimaryContainer

    Button(
        enabled = enabled,
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) pressedContainerColor else MaterialTheme.colorScheme.primary,
                contentColor = if (isPressed) pressedContentColor else MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
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
