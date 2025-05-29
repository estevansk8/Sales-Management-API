package com.api.sales_management.application.dto.request.product

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ProductRequestDTO(
    @field:NotBlank(message = "Product name is required")
    @field:Size(max = 100, message = "Product name must be up to 100 characters")
    val name: String,

    @field:NotNull(message = "Price is required")
    @field:DecimalMin(value = "0.01", message = "Price must be positive")
    @field:Digits(integer = 8, fraction = 2, message = "Price format invalid (e.g., 12345678.99)")
    val price: BigDecimal,

    @field:NotNull(message = "Stock is required")
    @field:Min(value = 0, message = "Stock cannot be negative")
    val stock: Int
)

