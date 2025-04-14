package com.dadadadev.superfinancer.data.goals

import com.dadadadev.superfinancer.data.goals.room.dao.GoalEntity
import kotlinx.coroutines.flow.Flow

interface GoalsDataSource {
    fun getAllGoals() : Flow<List<GoalEntity>>
    suspend fun insertGoal(goal: GoalEntity)
    suspend fun deleteGoal(id: Int)
}