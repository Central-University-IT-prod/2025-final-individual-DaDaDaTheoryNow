package com.dadadadev.superfinancer.domain.usecases.refills

import com.dadadadev.superfinancer.data.refills.RefillsDataSource
import com.dadadadev.superfinancer.domain.models.Refill
import com.dadadadev.superfinancer.domain.models.RefillType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllRefillsUseCase(
    private val refillsDataSource: RefillsDataSource
) {
    operator fun invoke(): Flow<List<Refill>> {
        return refillsDataSource
            .getAllRefills()
            .map {
                it.map { refill ->
                    Refill(
                        id = refill.id,
                        amount = refill.amount,
                        type = RefillType.fromInt(refill.amount.toInt()),
                        targetGoalName = refill.targetGoalName,
                        targetGoalId = refill.targetGoalId
                    )
                }
            }
    }
}