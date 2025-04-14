package com.dadadadev.superfinancer.feature.finances.view_model

import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.Refill

sealed interface FinancesAction {
    data class InsertGoal(val goal: Goal) : FinancesAction
    data class DeleteGoal(val id: Int) : FinancesAction
    data class EditGoal(val goal: Goal) : FinancesAction

    data class InsertRefill(val refill: Refill) : FinancesAction
    data class DeleteRefill(val id: Int) : FinancesAction
}