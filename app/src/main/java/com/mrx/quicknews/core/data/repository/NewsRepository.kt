package com.mrx.quicknews.core.data.repository

import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.NewsResponse

interface NewsRepository {
    suspend fun topHeadline(country: String, currentPage: Int): BaseResponse<NewsResponse>
}