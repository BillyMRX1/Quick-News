package com.mrx.quicknews

import android.app.Application
import com.mrx.quicknews.di.provideDataSourceModule
import com.mrx.quicknews.di.provideNetworkModule
import com.mrx.quicknews.di.provideRepositoryModule
import com.mrx.quicknews.di.provideUseCaseModule
import com.mrx.quicknews.di.provideViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NewsApplication)
            modules(
                provideNetworkModule,
                provideDataSourceModule,
                provideRepositoryModule,
                provideUseCaseModule,
                provideViewModelModule
            )
        }
    }
}