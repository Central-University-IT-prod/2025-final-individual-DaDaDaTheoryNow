package com.dadadadev.superfinancer.data.stocks

import com.dadadadev.result.ResponseResult
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.stocks.model.NetworkStock

interface StocksDataSource {
    suspend fun getPopularStocks(): ResponseResult<List<NetworkStock>, NetworkError>

    suspend fun searchStocks(
        query: String
    ) : ResponseResult<List<NetworkStock>, NetworkError>
}