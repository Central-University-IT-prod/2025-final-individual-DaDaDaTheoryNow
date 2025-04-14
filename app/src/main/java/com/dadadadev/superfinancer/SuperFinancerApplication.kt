package com.dadadadev.superfinancer

import android.app.Application
import com.dadadadev.social_feed.di.socialFeedModule
import com.dadadadev.superfinancer.di.appModule
import com.dadadadev.superfinancer.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SuperFinancerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SuperFinancerApplication)
            modules(databaseModule, appModule, socialFeedModule)
            //modules(databaseModule, repositoryModule, viewModelModule, appModules)
        }
    }
}