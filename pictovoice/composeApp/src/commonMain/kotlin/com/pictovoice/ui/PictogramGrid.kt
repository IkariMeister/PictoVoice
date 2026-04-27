package com.pictovoice.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram
import com.pictovoice.core.ui.PictoVoiceTheme

private val MinTouchTarget = 48.dp
internal fun pictogramCellDescription(label: String): String = "Add $label"

@Composable
fun PictogramGrid(
    pictograms: List<Pictogram>,
    onPictogramSelected: (Pictogram) -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = 2,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(pictograms, key = { it.id }) { pictogram ->
            PictogramCell(pictogram = pictogram, onClick = { onPictogramSelected(pictogram) })
        }
    }
}

@Composable
private fun PictogramCell(
    pictogram: Pictogram,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val containerColor =
        if (isPressed) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val contentColor =
        if (isPressed) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier =
            Modifier
                .heightIn(min = MinTouchTarget)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ),
                .semantics {
                    role = Role.Button
                    contentDescription = pictogramCellDescription(pictogram.label)
                },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = pictogram.label,
                color = contentColor,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Composable
private fun PictogramGridPreview() {
    PictoVoiceTheme {
        PictogramGrid(
            pictograms =
                listOf(
                    Pictogram("yes", "Yes", "Yes"),
                    Pictogram("no", "No", "No"),
                    Pictogram("water", "Water", "Water"),
                    Pictogram("help", "Help", "Help"),
                ),
            onPictogramSelected = {},
        )
    }
}
