package com.dadadadev.superfinancer.feature.general.components.news_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NewsItemInfo(
    source: String,
    publishedDate: String,
) {
    Column {
        Text(
            text = "Источник: $source",
            color = Color.White.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Опубликовано: $publishedDate",
            color = Color.White.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}