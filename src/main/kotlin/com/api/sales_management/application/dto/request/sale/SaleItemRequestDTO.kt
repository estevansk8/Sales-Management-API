package com.api.sales_management.application.dto.request.sale

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class SaleItemRequestDTO(
    @field:NotNull(message = "Product ID is required")
    val productId: Long,

    @field:NotNull(message = "Quantity is required")
    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity: Int,

    @field:NotNull(message = "Unit price is required")
    @field:DecimalMin(value = "0.01", message = "Unit price must be positive")
    @field:Digits(integer = 8, fraction = 2, message = "Unit price format invalid")
    val unitPrice: BigDecimal
)
