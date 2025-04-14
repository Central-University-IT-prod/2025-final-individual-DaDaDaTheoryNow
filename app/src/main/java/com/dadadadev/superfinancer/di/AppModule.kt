package com.dadadadev.superfinancer.di

import com.dadadadev.superfinancer.core.http_client.HttpClientFactory
import com.dadadadev.superfinancer.data.goals.GoalsDataSource
import com.dadadadev.superfinancer.data.goals.room.RoomGoalsImpl
import com.dadadadev.superfinancer.data.news.NewsDataSource
import com.dadadadev.superfinancer.data.news.network.NetworkNewsImpl
import com.dadadadev.superfinancer.data.refills.RefillsDataSource
import com.dadadadev.superfinancer.data.refills.room.RoomRefillsImpl
import com.dadadadev.superfinancer.data.stocks.StocksDataSource
import com.dadadadev.superfinancer.data.stocks.network.NetworkStocksImpl
import com.dadadadev.superfinancer.domain.usecases.goals.DeleteGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.EditGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.GetAllGoalsUseCase
import com.dadadadev.superfinancer.domain.usecases.news.GetPopularNewsUseCase
import com.dadadadev.superfinancer.domain.usecases.stocks.GetPopularStocksUseCase
import com.dadadadev.superfinancer.domain.usecases.goals.InsertGoalUseCase
import com.dadadadev.superfinancer.domain.usecases.news.SearchNewsUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.DeleteRefillUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.EditListOfRefillsUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.GetAllRefillsUseCase
import com.dadadadev.superfinancer.domain.usecases.refills.InsertRefillUseCase
import com.dadadadev.superfinancer.domain.usecases.stocks.SearchStocksUseCase
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesViewModel
import com.dadadadev.superfinancer.feature.general.view_model.GeneralViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(get()) }

    // repository
    singleOf(::NetworkNewsImpl).bind<NewsDataSource>()
    singleOf(::NetworkStocksImpl).bind<StocksDataSource>()
    singleOf(::RoomGoalsImpl).bind<GoalsDataSource>()
    singleOf(::RoomRefillsImpl).bind<RefillsDataSource>()

    // viewModel
    viewModelOf(::GeneralViewModel)
    viewModelOf(::FinancesViewModel)

    // UseCases
    singleOf(::GetPopularNewsUseCase)
    singleOf(::GetPopularStocksUseCase)
    singleOf(::SearchNewsUseCase)
    singleOf(::SearchStocksUseCase)
    singleOf(::GetAllGoalsUseCase)
    singleOf(::InsertGoalUseCase)
    singleOf(::DeleteGoalUseCase)
    singleOf(::EditGoalUseCase)

    singleOf(::DeleteRefillUseCase)
    singleOf(::GetAllRefillsUseCase)
    singleOf(::InsertRefillUseCase)
    singleOf(::EditListOfRefillsUseCase)
}