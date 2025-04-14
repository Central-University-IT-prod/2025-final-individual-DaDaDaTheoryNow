package com.dadadadev.superfinancer.data.refills.room

import com.dadadadev.superfinancer.data.refills.RefillsDataSource
import com.dadadadev.superfinancer.data.refills.room.dao.RefillEntity
import com.dadadadev.superfinancer.data.refills.room.dao.RefillsDao
import kotlinx.coroutines.flow.Flow

class RoomRefillsImpl(
    private val refillsDao: RefillsDao
) : RefillsDataSource {
    override fun getAllRefills(): Flow<List<RefillEntity>> {
        return refillsDao.getAllRefills()
    }

    override suspend fun insertRefill(refill: RefillEntity) {
        refillsDao.insertRefill(refill)
    }

    override suspend fun insertListOfRefills(refills: List<RefillEntity>) {
        refillsDao.insertListOfRefills(refills)
    }

    override suspend fun deleteRefill(id: Int) {
        refillsDao.deleteRefill(id)
    }
}