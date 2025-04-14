package com.dadadadev.superfinancer.domain.models

data class Stock(
    val name: String,
    val currentPrice: Double,
    val percentChange: Double?,
    val webUrl: String?,
    val logoUrl: String?,
)