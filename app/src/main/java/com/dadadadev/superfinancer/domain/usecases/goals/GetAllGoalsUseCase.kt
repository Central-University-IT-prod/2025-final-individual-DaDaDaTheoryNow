package com.dadadadev.superfinancer.domain.usecases.goals

import com.dadadadev.superfinancer.data.goals.GoalsDataSource
import com.dadadadev.superfinancer.domain.models.Goal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllGoalsUseCase(
    private val goalsDataSource: GoalsDataSource
) {
    operator fun invoke(): Flow<List<Goal>> {
        return goalsDataSource
            .getAllGoals()
            .map {
                it.map { goal ->
                    Goal(
                        id = goal.id,
                        text = goal.text,
                        currentValue = goal.currentValue,
                        targetValue = goal.targetValue
                    )
                }
            }
    }
}