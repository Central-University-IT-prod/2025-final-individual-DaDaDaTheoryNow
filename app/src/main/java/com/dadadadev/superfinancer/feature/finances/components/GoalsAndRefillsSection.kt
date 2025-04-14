package com.dadadadev.superfinancer.feature.finances.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.feature.finances.components.goal_item.GoalsSection
import com.dadadadev.superfinancer.feature.finances.components.refill_item.RefillSection

@Composable
fun GoalsAndRefillsSection(
    goals: List<Goal>,
    refills: List<Refill>,
    isGoalsExpanded: Boolean,
    isRefillsExpanded: Boolean,
    onGoalsExpand: () -> Unit,
    onRefillsExpand: () -> Unit,
    onAddGoal: () -> Unit,
    onEditGoal: (Int) -> Unit,
    onDeleteGoal: (Int) -> Unit,
    onAddRefill: () -> Unit,
    onDeleteRefill: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    GoalsSection(
        goals = goals,
        onAddGoal = onAddGoal,
        onDeleteGoal = onDeleteGoal,
        onEditGoal = onEditGoal,
        isExpanded = isGoalsExpanded,
        onExpand = onGoalsExpand,
        modifier = modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(16.dp))

    RefillSection(
        refills = refills,
        onAddRefill = onAddRefill,
        onDeleteRefill = onDeleteRefill,
        isExpanded = isRefillsExpanded,
        onExpand = onRefillsExpand,
        modifier = modifier.fillMaxWidth()
    )
}