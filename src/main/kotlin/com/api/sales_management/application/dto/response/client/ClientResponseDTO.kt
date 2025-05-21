package com.api.sales_management.application.dto.response.client

import java.time.LocalDateTime

data class ClientResponseDTO(
    val id: Long,
    val name: String,
    val phone: String?,
    val address: String?,
    val userId: Long,
    val userName: String,
    val createdAt: LocalDateTime
)
