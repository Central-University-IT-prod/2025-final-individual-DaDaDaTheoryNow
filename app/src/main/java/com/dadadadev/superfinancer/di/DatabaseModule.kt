package com.dadadadev.superfinancer.di

import android.content.Context
import androidx.room.Room
import com.dadadadev.superfinancer.core.Constants
import com.dadadadev.superfinancer.core.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single {
        Room
            .databaseBuilder(
                get<Context>(),
                AppDatabase::class.java,
                Constants.DATABASE
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().goalsDao }
    single { get<AppDatabase>().refillsDao }
}