package com.api.sales_management.shared.dto

data class ApiResponseDTO<T>(
    val sucess: Boolean,
    val message: String,
    val data: T? = null,
)
