package com.dadadadev.superfinancer.domain.usecases.refills

import com.dadadadev.superfinancer.data.refills.RefillsDataSource
import com.dadadadev.superfinancer.data.refills.room.dao.RefillEntity
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.domain.models.RefillType

class InsertRefillUseCase(
    private val refillsDataSource: RefillsDataSource
) {
    suspend operator fun invoke(refill: Refill) {
        refillsDataSource.insertRefill(
            RefillEntity(
                amount = when (refill.type) {
                    RefillType.Increment -> {
                        refill.amount
                    }
                    RefillType.Decrement -> {
                        -refill.amount
                    }
                },
                targetGoalName = refill.targetGoalName,
                targetGoalId = refill.targetGoalId
            )
        )
    }
}