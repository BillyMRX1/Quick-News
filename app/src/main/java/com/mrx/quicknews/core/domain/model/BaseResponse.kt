package com.mrx.quicknews.core.domain.model

sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T) : BaseResponse<T>()
    data class Error(val errorResponse: ErrorResponse) : BaseResponse<Nothing>()
}
