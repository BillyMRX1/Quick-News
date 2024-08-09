package com.mrx.quicknews.core.data.datasource.remote

import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.NewsResponse
import com.mrx.quicknews.core.service.ApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsRemoteDataSourceImpl : NewsRemoteDataSource, KoinComponent {
    private val apiService: ApiService by inject()
    override suspend fun topHeadline(country: String, currentPage: Int): BaseResponse<NewsResponse> =
        apiService.topHeadline(country, currentPage)
}