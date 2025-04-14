package com.dadadadev.superfinancer.data.stocks.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkSearchedStockResult(
    val symbol: String,
)

@Serializable
data class NetworkStockSearch(
    val result: List<NetworkSearchedStockResult>?
)