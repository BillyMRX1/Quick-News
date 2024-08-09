package com.mrx.quicknews.core.domain.model

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)