package com.api.sales_management.application.dto.response.sale

import java.math.BigDecimal

data class SaleItemResponseDTO(
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val subtotal: BigDecimal
)
