package com.api.sales_management.domain.model.valueobject

import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Size

@Embeddable
data class AddressVO(
    @field:Size(max = 200, message = "Address must be up to 200 characters")
    val value: String?
) {
    // No futuro, poderia ser expandido para:
    // val street: String,
    // val city: String,
    // val zipCode: String,
    // val country: String
    // E as colunas no banco de dados refletiriam essa estrutura.
    override fun toString(): String = value ?: ""
}
