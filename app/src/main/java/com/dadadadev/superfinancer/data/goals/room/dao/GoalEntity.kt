package com.dadadadev.superfinancer.data.goals.room.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val text: String,
    val currentValue: Long,
    val targetValue: Long,
)