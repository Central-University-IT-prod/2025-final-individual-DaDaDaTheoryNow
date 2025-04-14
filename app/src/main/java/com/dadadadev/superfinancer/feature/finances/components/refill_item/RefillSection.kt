package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Refill

@Composable
fun RefillSection(
    refills: List<Refill>,
    onAddRefill: () -> Unit,
    onDeleteRefill: (Int) -> Unit,
    onExpand: () -> Unit,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column {
            RefillHeader(
                showMoreButtonVisible = refills.isNotEmpty(),
                isExpanded = isExpanded,
                onAddClick = {
                    onAddRefill()
                },
                onExpandClick = { onExpand() }
            )

            if (isExpanded && refills.isNotEmpty()) {
                HorizontalDivider(thickness = 2.dp)
            }

            RefillList(
                refills = refills,
                isExpanded = isExpanded,
                onDeleteRefill = onDeleteRefill,
            )
        }
    }
}