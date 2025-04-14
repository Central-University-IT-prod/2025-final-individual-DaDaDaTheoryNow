package com.dadadadev.superfinancer.domain.models

data class Goal(
    val id: Int = 0,
    val text: String,
    val currentValue: Long,
    val valueFromRefills: Long = 0,
    val targetValue: Long,
)