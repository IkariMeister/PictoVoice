package com.pictovoice.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pictovoice.core.model.Pictogram

@Composable
fun PictogramGrid(
    pictograms: List<Pictogram>,
    onPictogramSelected: (Pictogram) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 132.dp),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 4.dp),
    ) {
        items(
            items = pictograms,
            key = { pictogram -> pictogram.id },
        ) { pictogram ->
            Button(
                onClick = { onPictogramSelected(pictogram) },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 88.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = pictogram.label,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
