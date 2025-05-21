package com.api.sales_management.domain.model.valueobject

import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Pattern

@Embeddable
data class PhoneVO(

    // Permite formatos como (XX) XXXXX-XXXX ou XX XXXXXXXX, etc.
    @field:Pattern(
        regexp = "^(\\([0-9]{2}\\))?[ ]?([0-9]{4,5})[- ]?([0-9]{4})?\$|^[0-9]{10,11}\$",
        message = "Invalid phone number format"
    )
    val value: String?
) {
    init {
         value?.let {
            if (it.length < 8) throw IllegalArgumentException("Phone number too short")
         }
    }
    override fun toString(): String = value ?: ""
}