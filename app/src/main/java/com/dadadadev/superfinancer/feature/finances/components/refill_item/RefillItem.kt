package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.domain.models.RefillType

@Composable
fun RefillItem(
    refill: Refill,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val annotatedString = buildAnnotatedString {
            append("На -> ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(refill.targetGoalName)
            }
        }

        Text(
            text = annotatedString,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
        )

        Card {
            Text(
                text = "${refill.amount.toInt()}$",
                fontWeight = FontWeight.SemiBold,
                color = if (refill.type == RefillType.Increment) Color.Green else Color.Red,
                modifier = Modifier
                    .padding(16.dp)
            )
        }


        IconButton(
            onClick = {
                onDelete()
            }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}