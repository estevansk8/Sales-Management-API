package com.api.sales_management.application.dto

data class ApiResponseDTO<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
)