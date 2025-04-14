package com.dadadadev.superfinancer.feature.general.view_model

sealed interface GeneralAction {
    data class SearchNews(val query: String) : GeneralAction
    data object TogglePullToRefresh : GeneralAction
}