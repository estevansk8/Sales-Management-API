package com.api.sales_management.application.dto.request.client

import com.api.sales_management.domain.model.valueobject.AddressVO
import com.api.sales_management.domain.model.valueobject.PhoneVO
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ClientRequestDTO(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100)
    val name: String,

    @field:Valid
    val phone: PhoneVO?,

    @field:Valid
    val address: AddressVO?,

    @field:NotNull(message = "User ID is required")
    val userId: Long
)