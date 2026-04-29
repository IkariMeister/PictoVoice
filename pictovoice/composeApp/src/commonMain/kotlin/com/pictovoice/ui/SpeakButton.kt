package com.pictovoice.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun SpeakButton(
    enabled: Boolean,
    onSpeak: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(
        onClick = onSpeak,
        enabled = enabled,
        interactionSource = interactionSource,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) Color(0xFFFFFFFF) else Color(0xFFFFEB3B),
                contentColor = Color(0xFF000000),
                disabledContainerColor = Color(0xFF4C4C4C),
                disabledContentColor = Color(0xFFBDBDBD),
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .border(
                    width = if (isPressed) 2.dp else 1.dp,
                    color = if (isPressed) Color(0xFFFFEB3B) else Color(0xFF2D2D2D),
                )
                .semantics { contentDescription = "Speak sentence" },
    ) {
        Text("Speak")
    }
}
