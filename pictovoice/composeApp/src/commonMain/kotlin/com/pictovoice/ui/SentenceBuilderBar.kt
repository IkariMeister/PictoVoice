package com.pictovoice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram

@Composable
fun SentenceBuilderBar(
    sentencePictograms: List<Pictogram>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (sentencePictograms.isEmpty()) {
            Text(text = "…", color = MaterialTheme.colorScheme.onSurface)
        } else {
            sentencePictograms.forEach { pictogram ->
                Text(text = pictogram.label, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
