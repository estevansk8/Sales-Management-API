package com.api.sales_management.shared.dto

data class ApiResponseDTO<T>(
    val data: T? = null,
    val sucess: Boolean,
    val message: String,
)
