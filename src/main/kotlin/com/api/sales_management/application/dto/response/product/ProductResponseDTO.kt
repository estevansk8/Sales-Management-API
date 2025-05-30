package com.api.sales_management.application.dto.response.product

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponseDTO(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
)
