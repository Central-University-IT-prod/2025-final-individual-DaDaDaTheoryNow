package com.dadadadev.superfinancer.data.stocks.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkQuote(
    @SerialName("c") val current: Double,
    @SerialName("h") val high: Double,
    @SerialName("l") val low: Double,
    @SerialName("dp") val percentChange: Double?,
)

@Serializable
data class NetworkDetailsStock(
    @SerialName("weburl") val webUrl: String? = null,
    @SerialName("logo") val logoUrl: String? = null,
    val ticker: String? = null,
)

data class NetworkStock(
    val name: String,
    val details: NetworkDetailsStock,
    val quoteDetails: NetworkQuote,
)
