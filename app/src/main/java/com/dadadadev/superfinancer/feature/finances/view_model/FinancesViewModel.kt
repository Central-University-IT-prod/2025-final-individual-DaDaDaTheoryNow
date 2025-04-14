package com.dadadadev.superfinancer.feature.finances.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.domain.usecases.goals.DeleteGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.EditGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.GetAllGoalsUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.InsertGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.DeleteRefillUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.EditListOfRefillsUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.GetAllRefillsUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.InsertRefillUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FinancesViewModel(
    getAllRefillsUseCase: GetAllRefillsUseCase,
    getAllGoalsUseCase: GetAllGoalsUseCase,
    val insertGoalUseCase: InsertGoalUseCase,
    val editGoalUseCase: EditGoalUseCase,
    val deleteGoalUseCase: DeleteGoalUseCase,
    val deleteRefillUseCase: DeleteRefillUseCase,
    val insertRefillUseCase: InsertRefillUseCase,
    val editListOfRefillsUseCase: EditListOfRefillsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FinancesState())
    val state = _state.asStateFlow()

    private val goalsFlow = getAllGoalsUseCase()
    private val refillsFlow = getAllRefillsUseCase()

    init {
        observeGoalsAndRefills()
    }

    private fun observeGoalsAndRefills() {
        combine(goalsFlow, refillsFlow) { goals, refills ->
            Pair(goals, refills)
        }.onEach { (goals, refills) ->
            val validatedRefills = refills.filter { refill ->
                goals.any { it.id == refill.targetGoalId }
            }

            val invalidRefills = refills - validatedRefills.toSet()
            invalidRefills.forEach { refill ->
                onAction(FinancesAction.DeleteRefill(refill.id))
            }

            updateState(goals, validatedRefills)
        }.launchIn(viewModelScope)
    }

    private fun updateState(goals: List<Goal>, refills: List<Refill>) {
        val updatedGoals = goals.map { goal ->
            val sum = refills
                .filter { it.targetGoalId == goal.id }
                .sumOf { it.amount }
                .toLong()

            goal.copy(valueFromRefills = sum)
        }

        val currentAmount = updatedGoals.sumOf {
            it.currentValue + it.valueFromRefills
        }

        val percentage = calculateCompletionPercentage(updatedGoals) {
            it.currentValue + it.valueFromRefills >= it.targetValue
        }

        _state.update {
            it.copy(
                goals = updatedGoals,
                refills = refills,
                targetAmount = updatedGoals.sumOf { goal -> goal.targetValue },
                currentAmount = currentAmount,
                percentageOfFinishedGoals = percentage
            )
        }
    }


    fun onAction(action: FinancesAction) {
        when (action) {
            is FinancesAction.InsertGoal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    insertGoalUseCase(action.goal)
                }
            }

            is FinancesAction.DeleteGoal ->  {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteGoalUseCase(action.id)
                }
            }

            is FinancesAction.EditGoal ->  {
                viewModelScope.launch(Dispatchers.IO) {
                    changeRefillNameOnEditGoal(action.goal.text, action.goal.id)
                    editGoalUseCase(action.goal)
                }
            }

            is FinancesAction.DeleteRefill -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteRefillUseCase(action.id)
                }
            }
            is FinancesAction.InsertRefill -> {
                viewModelScope.launch(Dispatchers.IO) {
                    insertRefillUseCase(action.refill)
                }
            }
        }
    }

    private suspend fun changeRefillNameOnEditGoal(newName: String, dependOnGoalId: Int) {
        val updatedRefills = _state.value.refills
            .filter {
                it.targetGoalId == dependOnGoalId
            }
            .map {
                it.copy(targetGoalName = newName)
            }

        editListOfRefillsUseCase(updatedRefills)
    }

    private inline fun calculateCompletionPercentage(
        goals: List<Goal>,
        condition: (Goal) -> Boolean
    ): Float = if (goals.isEmpty()) 1f else goals.count(condition).toFloat() / goals.size
}