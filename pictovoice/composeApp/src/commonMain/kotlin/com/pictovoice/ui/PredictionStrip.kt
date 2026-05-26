package com.pictovoice.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.ui.PictoVoiceTheme

@Composable
fun PredictionStrip(
    predictions: List<Pictogram>,
    onPredictionTapped: (Pictogram) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        predictions.take(4).forEach { prediction ->
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(12.dp),
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onPredictionTapped(prediction) }
                        .semantics {
                            contentDescription = "Prediction ${prediction.label}"
                        },
            ) {
                Text(
                    text = prediction.label,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                )
            }
        }
    }
}

@Composable
private fun PredictionStripPreview() {
    PictoVoiceTheme {
        PredictionStrip(
            predictions =
                listOf(
                    Pictogram("yes", "Yes", "Yes"),
                    Pictogram("water", "Water", "Water"),
                ),
            onPredictionTapped = {},
        )
    }
}
