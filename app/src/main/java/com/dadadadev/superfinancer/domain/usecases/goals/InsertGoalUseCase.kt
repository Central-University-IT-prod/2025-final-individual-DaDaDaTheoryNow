package com.dadadadev.superfinancer.domain.usecases.goals

import com.dadadadev.superfinancer.data.goals.GoalsDataSource
import com.dadadadev.superfinancer.data.goals.room.dao.GoalEntity
import com.dadadadev.superfinancer.domain.models.Goal

class InsertGoalUseCase(
    private val goalsDataSource: GoalsDataSource
) {
    suspend operator fun invoke(goal: Goal) {
        goalsDataSource.insertGoal(
            GoalEntity(
                text = goal.text,
                currentValue = goal.currentValue,
                targetValue = goal.targetValue,
            )
        )
    }
}