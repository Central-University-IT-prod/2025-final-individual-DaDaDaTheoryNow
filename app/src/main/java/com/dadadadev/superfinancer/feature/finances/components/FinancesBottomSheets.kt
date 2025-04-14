package com.dadadadev.superfinancer.feature.finances.components

import androidx.compose.runtime.Composable
import com.dadadadev.superfinancer.domain.models.RefillType
import com.dadadadev.superfinancer.feature.finances.components.goal_item.AddGoalBottomSheetContent
import com.dadadadev.superfinancer.feature.finances.components.goal_item.EditGoalBottomSheetContent
import com.dadadadev.superfinancer.feature.finances.components.goal_item.GoalBottomSheet
import com.dadadadev.superfinancer.feature.finances.components.refill_item.AddRefillBottomSheetContent
import com.dadadadev.superfinancer.feature.finances.components.refill_item.RefillBottomSheet
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesState

@Composable
fun FinancesBottomSheets(
    state: FinancesState,
    showAddGoalSheet: Boolean,
    editGoalSheetId: Int?,
    showAddRefillSheet: Boolean,
    onDismissAddGoal: () -> Unit,
    onDismissEditGoal: () -> Unit,
    onDismissAddRefill: () -> Unit,
    onAddGoal: (String, Long, Long) -> Unit,
    onEditGoal: (String, Long, Long) -> Unit,
    onAddRefill: (String, RefillType, amount: Long, goalId: Int) -> Unit,
) {
    if (showAddGoalSheet) {
        GoalBottomSheet(
            changeBottomSheetVisibility = { visible ->
                if (!visible) onDismissAddGoal()
            }
        ) {
            AddGoalBottomSheetContent(
                onDismiss = onDismissAddGoal,
                onAddGoal = onAddGoal
            )
        }
    }

    if (editGoalSheetId != null) {
        GoalBottomSheet(
            changeBottomSheetVisibility = { visible ->
                if (!visible) onDismissEditGoal()
            }
        ) {
            EditGoalBottomSheetContent(
                onDismiss = onDismissEditGoal,
                goal = state.goals.first { it.id == editGoalSheetId },
                onChange = { text, currentValue, targetValue ->
                    onEditGoal(text, currentValue, targetValue)
                    onDismissEditGoal()
                }
            )
        }
    }

    if (showAddRefillSheet) {
        RefillBottomSheet(
            changeBottomSheetVisibility = { visible ->
                if (!visible) onDismissAddRefill()
            }
        ) {
            AddRefillBottomSheetContent(
                goals = state.goals,
                onDismiss = onDismissAddRefill,
                onAddRefill = onAddRefill,
            )
        }
    }
}