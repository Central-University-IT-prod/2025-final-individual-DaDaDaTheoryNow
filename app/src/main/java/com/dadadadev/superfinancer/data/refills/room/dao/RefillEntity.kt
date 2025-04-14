package com.dadadadev.superfinancer.data.refills.room.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refills")
data class RefillEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var amount: Long,
    var targetGoalName: String,
    var targetGoalId: Int,
)