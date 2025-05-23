package com.api.sales_management.application.dto.request.user

import jakarta.validation.constraints.Size

data class UserUpdateRequestDTO(
    @field:Size(max = 100, message = "Name must be up to 100 characters")
    val name: String?,

    val email: String?,

    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String?,

    val profilePicture: String?
)

