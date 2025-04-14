package com.dadadadev.superfinancer.domain.usecases.goals

import com.dadadadev.superfinancer.data.goals.GoalsDataSource

class DeleteGoalUseCase(
    private val goalsDataSource: GoalsDataSource
) {
    suspend operator fun invoke(id: Int) {
        goalsDataSource.deleteGoal(id)
    }
}