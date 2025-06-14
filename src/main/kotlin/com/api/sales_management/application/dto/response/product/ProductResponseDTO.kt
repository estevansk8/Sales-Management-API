package com.api.sales_management.application.dto.response.product

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductResponseDTO(
    val id: Long,
    val name: String,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    val price: BigDecimal,

    val stock: Int,
)
