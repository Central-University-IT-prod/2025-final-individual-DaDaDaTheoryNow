package com.dadadadev.superfinancer.domain.usecases.stocks

import com.dadadadev.result.ResponseResult
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.stocks.StocksDataSource
import com.dadadadev.superfinancer.domain.models.Stock

class SearchStocksUseCase(
    private val stocksDataSource: StocksDataSource
) {
    suspend operator fun invoke(query: String): ResponseResult<List<Stock>, NetworkError> {
        return when (val result = stocksDataSource.searchStocks(query)) {
            is ResponseResult.Success -> ResponseResult.Success(
                result.data.map { item ->
                    Stock(
                        name = item.name,
                        currentPrice = item.quoteDetails.current,
                        percentChange = item.quoteDetails.percentChange,
                        webUrl = item.details.webUrl,
                        logoUrl = item.details.logoUrl
                    )
                }
            )
            is ResponseResult.Error -> ResponseResult.Error(result.error)
        }
    }
}