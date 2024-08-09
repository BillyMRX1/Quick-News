package com.mrx.quicknews.core.service

import com.mrx.quicknews.BuildConfig
import com.mrx.quicknews.core.domain.model.BaseResponse
import com.mrx.quicknews.core.domain.model.ErrorResponse
import com.mrx.quicknews.core.domain.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApiService : KoinComponent {
    private val httpClient: HttpClient by inject()
    suspend fun topHeadline(
        country: String,
        currentPage: Int
    ): BaseResponse<NewsResponse> {
        return try {
            val apiUrl = "${BuildConfig.BASE_URL}/top-headlines"
            val response = httpClient.get(apiUrl) {
                url {
                    parameters.append("country", "us")
                    parameters.append("page", currentPage.toString())
                    parameters.append("apiKey", BuildConfig.API_KEY)
                }
            }
            if (response.status == HttpStatusCode.OK) {
                val newsResponse = response.body<NewsResponse>()
                BaseResponse.Success(newsResponse)
            } else {
                val errorResponse = response.body<ErrorResponse>()
                BaseResponse.Error(errorResponse)
            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            BaseResponse.Error(ErrorResponse("error", "unknown", "An unexpected error occurred"))
        }
    }
}