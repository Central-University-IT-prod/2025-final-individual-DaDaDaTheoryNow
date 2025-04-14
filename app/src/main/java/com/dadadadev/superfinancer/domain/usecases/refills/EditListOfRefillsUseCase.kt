package com.dadadadev.superfinancer.domain.usecases.refills

import com.dadadadev.superfinancer.data.refills.RefillsDataSource
import com.dadadadev.superfinancer.data.refills.room.dao.RefillEntity
import com.dadadadev.superfinancer.domain.models.Refill

class EditListOfRefillsUseCase(
    private val refillsDataSource: RefillsDataSource
) {
    suspend operator fun invoke(refills: List<Refill>) {
        val updatedRefills = refills.map { refill ->
            RefillEntity(
                id = refill.id,
                amount = refill.amount,
                targetGoalName = refill.targetGoalName,
                targetGoalId = refill.targetGoalId
            )
        }

        refillsDataSource.insertListOfRefills(
            updatedRefills
        )
    }
}