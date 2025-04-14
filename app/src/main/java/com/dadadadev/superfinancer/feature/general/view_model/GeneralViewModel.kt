package com.dadadadev.superfinancer.feature.general.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.result.ResponseResult
import com.dadadadev.superfinancer.domain.usecases.news.GetPopularNewsUseCase
import com.dadadadev.superfinancer.domain.usecases.stocks.GetPopularStocksUseCase
import com.dadadadev.superfinancer.domain.usecases.news.SearchNewsUseCase
import com.dadadadev.superfinancer.domain.usecases.stocks.SearchStocksUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class GeneralViewModel(
    private val getPopularNewsUseCase: GetPopularNewsUseCase,
    private val getPopularStocksUseCase: GetPopularStocksUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val searchStocksUseCase: SearchStocksUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GeneralState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery.asStateFlow()

    init {
        loadInitialData()
        setupSearchObserver()
    }

    fun onAction(action: GeneralAction) {
        when (action) {
            is GeneralAction.SearchNews -> {
                _searchQuery.value = action.query
            }
            GeneralAction.TogglePullToRefresh -> {
                handleRefresh()
            }
        }
    }

    private fun loadInitialData() {
        _state.update {
            it.copy(
                isNewsLoading = true,
                isStocksLoading = true
            )
        }

        viewModelScope.launch {
            getPopularNews()
            getPopularStocks()
        }
    }

    private fun setupSearchObserver() {
        viewModelScope.launch {
            searchQuery
                .debounce(350)
                .distinctUntilChanged()
                .collect { query ->
                    when {
                        query.length >= 2 -> performSearch(query)
                        else -> loadPopularData()
                    }
                }
        }
    }

    private fun handleRefresh() {
        _state.update { it.copy(isRefreshing = true) }

        viewModelScope.launch {
            if (_searchQuery.value.isNotEmpty()) {
                performSearch(_searchQuery.value)
            } else {
                loadPopularData()
            }

            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private suspend fun performSearch(query: String) {
        _state.update { it.copy(
            isStocksLoading = true,
            isNewsLoading = true
        ) }

        searchNews(query)
        searchStocks(query)
    }

    private suspend fun loadPopularData() {
        _state.update { it.copy(
            isStocksLoading = true,
            isNewsLoading = true
        ) }

        getPopularNews()
        getPopularStocks()
    }

    private suspend fun getPopularNews() {
        when (val result = getPopularNewsUseCase()) {
            is ResponseResult.Success -> {
                _state.update {
                    it.copy(
                        news = result.data,
                        isNewsLoading = false
                    )
                }
            }
            is ResponseResult.Error -> {
                _state.update { it.copy(isNewsLoading = false) }
            }
        }
    }

    private suspend fun getPopularStocks() {
        when (val result = getPopularStocksUseCase()) {
            is ResponseResult.Success -> {
                _state.update {
                    it.copy(
                        stocks = result.data,
                        isStocksLoading = false
                    )
                }
            }
            is ResponseResult.Error -> {
                _state.update { it.copy(isStocksLoading = false) }
            }
        }
    }

    private suspend fun searchNews(query: String) {
        when (val result = searchNewsUseCase(query)) {
            is ResponseResult.Success -> {
                _state.update {
                    it.copy(
                        news = result.data,
                        isNewsLoading = false
                    )
                }
            }
            is ResponseResult.Error -> {
                _state.update { it.copy(isNewsLoading = false) }
            }
        }
    }

    private suspend fun searchStocks(query: String) {
        when (val result = searchStocksUseCase(query)) {
            is ResponseResult.Success -> {
                _state.update {
                    it.copy(
                        stocks = result.data,
                        isStocksLoading = false
                    )
                }
            }
            is ResponseResult.Error -> {
                _state.update { it.copy(isStocksLoading = false) }
            }
        }
    }
}