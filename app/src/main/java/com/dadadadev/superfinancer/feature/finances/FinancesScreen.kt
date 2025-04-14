package com.dadadadev.superfinancer.feature.finances

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.designsystem.components.app_bar.NestedAppBarSection
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.feature.finances.components.TargetsSection
import com.dadadadev.superfinancer.feature.finances.components.FinancesBottomSheets
import com.dadadadev.superfinancer.feature.finances.components.GoalsAndRefillsSection
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesAction
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesState
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesViewModel
import org.koin.androidx.compose.koinViewModel


// I used AI to break down large components into smaller ones
// This allowed me to speed up development time and simplify the code.
@Composable
fun FinancesScreen(
    viewModel: FinancesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var isGoalsExpanded by rememberSaveable { mutableStateOf(false) }
    var isRefillsExpanded by rememberSaveable { mutableStateOf(false) }
    var showAddGoalSheet by remember { mutableStateOf(false) }
    var editGoalSheetId by remember { mutableStateOf<Int?>(null) }
    var showAddRefillSheet by remember { mutableStateOf(false) }

    FinancesContent(
        state = state,
        modifier = modifier,
        isGoalsExpanded = isGoalsExpanded,
        isRefillsExpanded = isRefillsExpanded,
        onGoalsExpand = { isGoalsExpanded = !isGoalsExpanded },
        onRefillsExpand = { isRefillsExpanded = !isRefillsExpanded },
        onAddGoalClick = { showAddGoalSheet = true },
        onEditGoalClick = { editGoalSheetId = it },
        onDeleteGoalClick = { viewModel.onAction(FinancesAction.DeleteGoal(it)) },
        onAddRefillClick = { showAddRefillSheet = true },
        onDeleteRefillClick = { viewModel.onAction(FinancesAction.DeleteRefill(it)) }
    )

    FinancesBottomSheets(
        state = state,
        showAddGoalSheet = showAddGoalSheet,
        editGoalSheetId = editGoalSheetId,
        showAddRefillSheet = showAddRefillSheet,
        onDismissAddGoal = { showAddGoalSheet = false },
        onDismissEditGoal = { editGoalSheetId = null },
        onDismissAddRefill = { showAddRefillSheet = false },
        onAddGoal = { text, current, target ->
            isGoalsExpanded = true
            viewModel.onAction(
                FinancesAction.InsertGoal(
                    Goal(
                        text = text,
                        currentValue = current,
                        targetValue = target
                    )
                )
            )
        },
        onEditGoal = { text, currentValue, targetValue ->
            isGoalsExpanded = true
            viewModel.onAction(
                FinancesAction.EditGoal(
                    goal = Goal(
                        id = editGoalSheetId!!,
                        text = text,
                        currentValue = currentValue,
                        targetValue = targetValue
                    ),
                )
            )
        },
        onAddRefill = { targetText, type, amount, goalId ->
            isRefillsExpanded = true
            viewModel.onAction(
                FinancesAction.InsertRefill(
                    refill = Refill(
                        amount = amount,
                        type = type,
                        targetGoalName = targetText,
                        targetGoalId = goalId
                    ),
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FinancesContent(
    state: FinancesState,
    modifier: Modifier,
    isGoalsExpanded: Boolean,
    isRefillsExpanded: Boolean,
    onGoalsExpand: () -> Unit,
    onRefillsExpand: () -> Unit,
    onAddGoalClick: () -> Unit,
    onEditGoalClick: (Int) -> Unit,
    onDeleteGoalClick: (Int) -> Unit,
    onAddRefillClick: () -> Unit,
    onDeleteRefillClick: (Int) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            NestedAppBarSection(
                title = "Финансы",
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding()
                ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                TargetsSection(state)
            }

            item {
                Spacer(Modifier.height(16.dp))
                GoalsAndRefillsSection(
                    goals = state.goals,
                    refills = state.refills,
                    isGoalsExpanded = isGoalsExpanded,
                    isRefillsExpanded = isRefillsExpanded,
                    onGoalsExpand = onGoalsExpand,
                    onRefillsExpand = onRefillsExpand,
                    onAddGoal = onAddGoalClick,
                    onEditGoal = onEditGoalClick,
                    onDeleteGoal = onDeleteGoalClick,
                    onAddRefill = onAddRefillClick,
                    onDeleteRefill = onDeleteRefillClick
                )
            }
        }
    }
}