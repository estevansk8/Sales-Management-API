package com.api.sales_management.application.dto.request.user

import com.api.sales_management.domain.model.valueobject.EmailVO
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequestDTO(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 100, message = "Name must be up to 100 characters")
    val name: String,

    val email: EmailVO,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String,

    val profilePicture: String? = null
)
