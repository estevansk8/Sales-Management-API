package com.api.sales_management.application.dto.response.sale

import com.api.sales_management.domain.model.enums.SaleStatus
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class SaleResponseDTO(
    val id: Long,
    val clientId: Long,
    val clientName: String,
    val saleDate: LocalDate,

    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    var totalAmount: BigDecimal,

    val status: SaleStatus,
    val dueDate: LocalDate?,
    val createdAt: LocalDateTime,
    val items: List<SaleItemResponseDTO>
)
