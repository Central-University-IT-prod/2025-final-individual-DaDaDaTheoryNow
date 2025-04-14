package com.dadadadev.superfinancer.feature.finances.view_model

import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.Refill

data class FinancesState(
    val currentAmount: Long = 0,
    val targetAmount: Long = 0,
    val percentageOfFinishedGoals: Float = 0f,
    val goals: List<Goal> = listOf(),
    val refills: List<Refill> = listOf(),
)