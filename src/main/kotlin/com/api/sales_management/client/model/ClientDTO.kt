package com.api.sales_management.client.model

import jakarta.validation.constraints.NotBlank

data class ClientDTO(
    val id: Long? = null,

    @field:NotBlank(message = "Name is required")
    val name: String,

    val phone: String?,

    val address: String?
)
