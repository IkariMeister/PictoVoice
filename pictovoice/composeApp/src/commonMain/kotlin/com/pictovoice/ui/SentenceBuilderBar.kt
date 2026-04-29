package com.pictovoice.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram

@Composable
fun SentenceBuilderBar(
    items: List<Pictogram>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .semantics { contentDescription = "Sentence builder bar" }
                .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (items.isEmpty()) {
            Text("Tap pictograms to build a sentence")
            return
        }

        items.forEach { pictogram ->
            AssistChip(
                onClick = {},
                modifier = Modifier.semantics { contentDescription = "Sentence item ${pictogram.label}" },
                label = { Text(pictogram.label) },
            )
        }
    }
}
