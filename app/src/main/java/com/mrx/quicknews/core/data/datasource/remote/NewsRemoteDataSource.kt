package com.mrx.quicknews.core.data.datasource.remote

import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.NewsResponse

interface NewsRemoteDataSource {
    suspend fun topHeadline(country: String, currentPage: Int): BaseResponse<NewsResponse>
}