package com.api.sales_management.application.dto.response.user

import java.time.LocalDateTime

data class UserResponseDTO(
    val id: Long,
    val name: String,
    val email: String,  
    val profilePicture: String?,
    val createdAt: LocalDateTime
)
