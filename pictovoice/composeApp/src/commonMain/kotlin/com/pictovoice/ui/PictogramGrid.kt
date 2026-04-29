package com.pictovoice.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram

@Composable
fun PictogramGrid(
    pictograms: List<Pictogram>,
    onSelectPictogram: (Pictogram) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier =
            modifier
                .fillMaxSize()
                .semantics { contentDescription = "Pictogram selection grid" },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = pictograms, key = { it.id }) { pictogram ->
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = if (isPressed) Color(0xFFFFEB3B) else Color(0xFF121212),
                        contentColor = if (isPressed) Color(0xFF000000) else Color(0xFFFFFFFF),
                    ),
                modifier =
                    Modifier
                        .heightIn(min = 56.dp)
                        .border(
                            width = if (isPressed) 2.dp else 1.dp,
                            color = if (isPressed) Color(0xFFFFFFFF) else Color(0xFF2D2D2D),
                        )
                        .semantics { contentDescription = "Pictogram ${pictogram.label}" }
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) { onSelectPictogram(pictogram) },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(pictogram.label)
                }
            }
        }
    }
}
