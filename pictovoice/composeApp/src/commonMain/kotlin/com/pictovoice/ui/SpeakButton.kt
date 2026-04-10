package com.pictovoice.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SpeakButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .semantics { role = Role.Button },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    ) {
        Text(
            text = "Speak",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
fun SpeakButtonPreview() {
    MaterialTheme {
        SpeakButton(enabled = true, onClick = {})
    }
}

@Preview
@Composable
fun SpeakButtonDisabledPreview() {
    MaterialTheme {
        SpeakButton(enabled = false, onClick = {})
    }
}
