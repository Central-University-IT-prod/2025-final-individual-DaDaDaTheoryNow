package com.dadadadev.superfinancer.data.goals.room

import com.dadadadev.superfinancer.data.goals.GoalsDataSource
import com.dadadadev.superfinancer.data.goals.room.dao.GoalEntity
import com.dadadadev.superfinancer.data.goals.room.dao.GoalsDao
import kotlinx.coroutines.flow.Flow

class RoomGoalsImpl(
    private val goalsDao: GoalsDao
) : GoalsDataSource {
    override fun getAllGoals(): Flow<List<GoalEntity>> {
        return goalsDao.getAllGoals()
    }

    override suspend fun insertGoal(goal: GoalEntity) {
        goalsDao.insertGoal(goal)
    }

    override suspend fun deleteGoal(id: Int) {
        goalsDao.deleteGoal(id)
    }
}