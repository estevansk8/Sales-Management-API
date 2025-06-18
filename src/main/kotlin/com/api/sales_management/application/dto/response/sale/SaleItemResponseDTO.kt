package com.api.sales_management.application.dto.response.sale

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal

data class SaleItemResponseDTO(
    val productId: Long,
    val productName: String,
    val quantity: Int,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    val unitPrice: BigDecimal,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    val subtotal: BigDecimal
)
