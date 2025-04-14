package com.dadadadev.superfinancer.domain.usecases.refills

import com.dadadadev.superfinancer.data.refills.RefillsDataSource

class DeleteRefillUseCase(
    private val refillsDataSource: RefillsDataSource
) {
    suspend operator fun invoke(id: Int) {
        refillsDataSource.deleteRefill(id)
    }
}