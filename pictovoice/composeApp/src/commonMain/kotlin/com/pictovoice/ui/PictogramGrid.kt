package com.pictovoice.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.pictovoice.domain.model.Pictogram
import org.jetbrains.compose.ui.tooling.preview.Preview

private val MinTouch = 48.dp

@Composable
fun PictogramGrid(
    pictograms: List<Pictogram>,
    onPictogramClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = 2,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(pictograms, key = { it.id }) { p ->
            Card(
                modifier =
                    Modifier
                        .heightIn(min = MinTouch)
                        .aspectRatio(1f)
                        .semantics { role = Role.Button }
                        .clickable { onPictogramClick(p.id) },
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = p.label,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PictogramGridPreview() {
    MaterialTheme {
        PictogramGrid(
            pictograms =
                listOf(
                    Pictogram("1", "Yes", "Yes", null, null, 0),
                    Pictogram("2", "No", "No", null, null, 1),
                    Pictogram("3", "Water", "Water", null, null, 2),
                    Pictogram("4", "Help", "Help", null, null, 3),
                ),
            onPictogramClick = {},
        )
    }
}
