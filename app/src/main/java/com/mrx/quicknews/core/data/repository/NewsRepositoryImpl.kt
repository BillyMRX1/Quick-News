package com.mrx.quicknews.core.data.repository

import com.mrx.quicknews.core.data.datasource.remote.NewsRemoteDataSource
import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.NewsResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsRepositoryImpl : NewsRepository, KoinComponent {
    private val remoteDataSource: NewsRemoteDataSource by inject()
    override suspend fun topHeadline(country: String, currentPage: Int): BaseResponse<NewsResponse> =
        remoteDataSource.topHeadline(country, currentPage)
}