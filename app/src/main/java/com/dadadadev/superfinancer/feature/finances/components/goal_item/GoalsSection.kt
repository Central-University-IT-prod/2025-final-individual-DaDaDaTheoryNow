package com.dadadadev.superfinancer.feature.finances.components.goal_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Goal

@Composable
fun GoalsSection(
    goals: List<Goal>,
    onAddGoal: () -> Unit,
    onEditGoal: (Int) -> Unit,
    onDeleteGoal: (Int) -> Unit,
    onExpand: () -> Unit,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier) {
        Column {
            GoalsHeader(
                showMoreButtonVisible = goals.isNotEmpty(),
                isExpanded = isExpanded,
                onAddClick = {
                    onAddGoal()
                },
                onExpandClick = { onExpand() }
            )

            if (isExpanded && goals.isNotEmpty()) {
                HorizontalDivider(thickness = 2.dp)
            }

            GoalsList(
                goals = goals,
                isExpanded = isExpanded,
                onDeleteGoal = onDeleteGoal,
                onEditGoal = onEditGoal
            )
        }
    }
}