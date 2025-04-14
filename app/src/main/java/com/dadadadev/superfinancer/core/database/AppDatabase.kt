package com.dadadadev.superfinancer.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dadadadev.superfinancer.data.goals.room.dao.GoalEntity
import com.dadadadev.superfinancer.data.goals.room.dao.GoalsDao
import com.dadadadev.superfinancer.data.refills.room.dao.RefillEntity
import com.dadadadev.superfinancer.data.refills.room.dao.RefillsDao

@Database(
    entities = [GoalEntity::class, RefillEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val goalsDao: GoalsDao
    abstract val refillsDao: RefillsDao
}