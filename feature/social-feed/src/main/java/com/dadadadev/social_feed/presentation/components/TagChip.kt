package com.dadadadev.social_feed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagChip(
    text: String,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(
                start = 8.dp,
                top = 4.dp,
                end = if (onDelete == null) 8.dp else 4.dp,
                bottom = 4.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )

            if (onDelete != null) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Удалить тег",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}