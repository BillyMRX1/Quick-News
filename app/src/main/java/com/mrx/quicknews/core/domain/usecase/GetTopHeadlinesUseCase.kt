package com.mrx.quicknews.core.domain.usecase

import com.mrx.quicknews.core.data.repository.NewsRepository
import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.NewsResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetTopHeadlinesUseCase : KoinComponent {
    private val newsRepository: NewsRepository by inject()
    suspend operator fun invoke(country: String, currentPage: Int): BaseResponse<NewsResponse> {
        return newsRepository.topHeadline(country, currentPage)
    }
}