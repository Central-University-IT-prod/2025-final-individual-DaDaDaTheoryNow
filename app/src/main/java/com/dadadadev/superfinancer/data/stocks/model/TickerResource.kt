package com.dadadadev.superfinancer.data.stocks.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerResource(
    val symbols: List<String>
)