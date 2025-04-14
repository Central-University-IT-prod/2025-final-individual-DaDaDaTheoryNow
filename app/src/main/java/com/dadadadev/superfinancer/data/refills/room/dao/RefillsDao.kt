package com.dadadadev.superfinancer.data.refills.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefillsDao {
    @Query("SELECT * FROM refills")
    fun getAllRefills(): Flow<List<RefillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefill(refill: RefillEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfRefills(refills: List<RefillEntity>)

    @Query("DELETE FROM refills WHERE id=:id")
    fun deleteRefill(id: Int)
}