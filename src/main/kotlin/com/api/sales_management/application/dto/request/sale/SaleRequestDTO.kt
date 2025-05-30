package com.api.sales_management.application.dto.request.sale

import com.api.sales_management.domain.model.enums.SaleStatus
import jakarta.validation.Valid
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class SaleRequestDTO(
    @field:NotNull(message = "Client ID is required")
    val clientId: Long,

    @field:NotNull(message = "Sale date is required")
    @field:PastOrPresent(message = "Sale date cannot be in the future")
    val saleDate: LocalDate,

    @field:NotNull(message = "Status is required")
    val status: SaleStatus,

    @field:FutureOrPresent(message = "Due date must be in the present or future")
    val dueDate: LocalDate? = null,

    @field:NotEmpty(message = "Sale must have at least one item")
    @field:Valid
    val items: List<SaleItemRequestDTO>
)