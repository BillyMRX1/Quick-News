package com.mrx.quicknews.di

import android.util.Log
import com.mrx.quicknews.core.data.datasource.remote.NewsRemoteDataSource
import com.mrx.quicknews.core.data.datasource.remote.NewsRemoteDataSourceImpl
import com.mrx.quicknews.core.data.repository.NewsRepository
import com.mrx.quicknews.core.data.repository.NewsRepositoryImpl
import com.mrx.quicknews.core.domain.usecase.GetTopHeadlinesUseCase
import com.mrx.quicknews.core.service.ApiService
import com.mrx.quicknews.viewmodel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val provideNetworkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }

                }
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
    singleOf(::ApiService)
}

val provideDataSourceModule = module {
    singleOf(::NewsRemoteDataSourceImpl).bind(NewsRemoteDataSource::class)
}

val provideRepositoryModule = module {
    singleOf(::NewsRepositoryImpl).bind(NewsRepository::class)
}

val provideUseCaseModule = module {
    singleOf(::GetTopHeadlinesUseCase)
}

val provideViewModelModule = module {
    viewModelOf(::HomeViewModel)
}