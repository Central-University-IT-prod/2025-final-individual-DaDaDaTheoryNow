package com.dadadadev.superfinancer.feature.general.view_model

import com.dadadadev.NewsArticle
import com.dadadadev.superfinancer.domain.models.Stock

data class GeneralState(
    val isRefreshing: Boolean = false,
    val isNewsLoading: Boolean = true,
    val isStocksLoading: Boolean = true,
    val news: List<NewsArticle> = listOf(),
    val stocks: List<Stock> = listOf(),
)