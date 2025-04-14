package com.dadadadev.superfinancer.data.stocks.network

import android.app.Application
import com.dadadadev.common.asset_manager.getDataFromJsonFile
import com.dadadadev.result.ResponseResult
import com.dadadadev.superfinancer.core.Constants
import com.dadadadev.superfinancer.core.http_client.safeCall
import com.dadadadev.result.NetworkError
import com.dadadadev.superfinancer.data.stocks.StocksDataSource
import com.dadadadev.superfinancer.data.stocks.model.NetworkDetailsStock
import com.dadadadev.superfinancer.data.stocks.model.NetworkQuote
import com.dadadadev.superfinancer.data.stocks.model.NetworkStock
import com.dadadadev.superfinancer.data.stocks.model.NetworkStockSearch
import com.dadadadev.superfinancer.data.stocks.model.TickerResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NetworkStocksImpl(
    private val context: Application,
    private val httpClient: HttpClient,
) : StocksDataSource {
    override suspend fun getPopularStocks(): ResponseResult<List<NetworkStock>, NetworkError> {
        val listOfStocks = getDataFromJsonFile<TickerResource>(
            context, TICKERS_ASSET
        ).symbols

        val result = mutableListOf<NetworkStock>()

        listOfStocks.forEach { stockId ->
            val details = getStockDetails(stockId)
            val quoteDetails = getStockQuoteDetails(stockId)

            if (details is ResponseResult.Success && quoteDetails is ResponseResult.Success) {
                val resultModel = NetworkStock(
                    stockId,
                    details.data,
                    quoteDetails.data
                )

                result.add(resultModel)
            }
        }

        return ResponseResult.Success(result)
    }

    override suspend fun searchStocks(query: String): ResponseResult<List<NetworkStock>, NetworkError> {
        val result = mutableListOf<NetworkStock>()
        val request = safeCall<NetworkStockSearch> {
            httpClient.get(
                "${Constants.BASE_STOCKS_API}/search"
            ) {
                parameter("q", query)
                parameter("exchange", "US")
                parameter("token", Constants.STOCKS_API_KEY)
            }
        }

        if (request is ResponseResult.Success) {
            request.data.result?.let {
                it.take(5).forEach { stockResult ->
                    val details = getStockDetails(stockResult.symbol)
                    val quoteDetails = getStockQuoteDetails(stockResult.symbol)

                    if (details is ResponseResult.Success && quoteDetails is ResponseResult.Success) {
                        val resultModel = NetworkStock(
                            stockResult.symbol,
                            details.data,
                            quoteDetails.data
                        )

                        result.add(resultModel)
                    }
                }
            }
        }

        return ResponseResult.Success(result)
    }

    private suspend fun getStockDetails(
        stockId: String
    ): ResponseResult<NetworkDetailsStock, NetworkError> {
        return safeCall<NetworkDetailsStock> {
            httpClient.get(
                "${Constants.BASE_STOCKS_API}/stock/profile2"
            ) {
                parameter("symbol", stockId)
                parameter("token", Constants.STOCKS_API_KEY)
            }
        }
    }

    private suspend fun getStockQuoteDetails(
        stockId: String
    ): ResponseResult<NetworkQuote, NetworkError> {
        return safeCall<NetworkQuote> {

            httpClient.get(
                "${Constants.BASE_STOCKS_API}/quote"
            ) {
                parameter("symbol", stockId)
                parameter("token", Constants.STOCKS_API_KEY)
            }
        }
    }

    companion object {
        private const val TICKERS_ASSET = "tickers.json"
    }
}