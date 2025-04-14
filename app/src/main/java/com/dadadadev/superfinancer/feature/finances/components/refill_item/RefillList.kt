package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Refill

@Composable
fun RefillList(
    refills: List<Refill>,
    isExpanded: Boolean,
    onDeleteRefill: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateContentSize()
            .height(if (isExpanded && refills.isNotEmpty()) Dp.Unspecified else 0.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        refills.forEach { refill ->
            RefillItem(
                refill = refill,
                onDelete = { onDeleteRefill(refill.id) }
            )
        }
    }
}