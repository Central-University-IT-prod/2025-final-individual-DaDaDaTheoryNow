package com.dadadadev.superfinancer.feature.finances.components.goal_item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Goal

@Composable
fun GoalsList(
    goals: List<Goal>,
    isExpanded: Boolean,
    onDeleteGoal: (Int) -> Unit,
    onEditGoal: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateContentSize()
            .height(if (isExpanded && goals.isNotEmpty()) Dp.Unspecified else 0.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        goals.forEach { goal ->
            GoalItem(
                goal = goal,
                onDelete = { onDeleteGoal(goal.id) },
                onEdit = { onEditGoal(goal.id) }
            )
        }
    }
}