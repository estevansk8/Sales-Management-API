package com.api.sales_management.application.dto.request.sale

import com.api.sales_management.domain.model.enums.SaleStatus
import jakarta.validation.constraints.NotNull

data class SaleUpdateStatusRequestDTO(
    @field:NotNull(message = "New status is required")
    val newStatus: SaleStatus
)
