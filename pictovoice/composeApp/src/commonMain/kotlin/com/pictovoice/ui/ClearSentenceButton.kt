package com.pictovoice.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.core.ui.PictoVoiceTheme

internal const val CLEAR_BUTTON_DESCRIPTION = "Clear sentence"

@Composable
fun ClearSentenceButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .semantics { contentDescription = CLEAR_BUTTON_DESCRIPTION },
    ) {
        Text(
            text = "Clear",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun ClearSentenceButtonEnabledPreview() {
    PictoVoiceTheme {
        ClearSentenceButton(enabled = true, onClick = {})
    }
}

@Composable
private fun ClearSentenceButtonDisabledPreview() {
    PictoVoiceTheme {
        ClearSentenceButton(enabled = false, onClick = {})
    }
}
