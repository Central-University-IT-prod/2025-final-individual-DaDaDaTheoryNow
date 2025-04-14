package com.dadadadev.superfinancer.data.refills

import com.dadadadev.superfinancer.data.refills.room.dao.RefillEntity
import kotlinx.coroutines.flow.Flow

interface RefillsDataSource {
    fun getAllRefills() : Flow<List<RefillEntity>>
    suspend fun insertRefill(refill: RefillEntity)
    suspend fun insertListOfRefills(refills: List<RefillEntity>)
    suspend fun deleteRefill(id: Int)
}