package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.feature.finances.components.goal_item.GoalItemNoActionsWithCheckbox

@Composable
fun GoalSelectionSection(
    goals: List<Goal>,
    showSelectGoalSheet: Boolean,
    selectedGoalId: String?,
    onGoalSelected: (goalId: String?, goalText: String) -> Unit,
    onToggleGoalSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedGoalId != null) {
                Text(
                    text = "Цель выбрана!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = "Выбрать из\nсуществующих целей",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }

            OutlinedButton(onClick = onToggleGoalSelection) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    }

    if (showSelectGoalSheet) {
        RefillBottomSheet(
            skipPartiallyExpanded = false,
            changeBottomSheetVisibility = { visible ->
                if (!visible) onToggleGoalSelection()
            }
        ) {
            if (goals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "У вас нет целей",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            if (goals.isNotEmpty())
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(goals) { goal ->
                    val isSelected = selectedGoalId == goal.id.toString()

                    Card(modifier = Modifier.fillMaxWidth()) {
                        GoalItemNoActionsWithCheckbox(
                            goal = goal,
                            isSelected = isSelected,
                            onCheckedChange = { value ->
                                onGoalSelected(
                                    if (value) goal.id.toString() else null,
                                    if (value) goal.text else ""
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}