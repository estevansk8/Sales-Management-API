package com.api.sales_management.domain.model.valueobject

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailVO(@field:NotBlank(message = "Email cannot be blank")
                   @field:Email(message = "Email should be valid")
                   val value: String
) {
    init {
        //TODO: verificar se o domínio do email é permitido.
    }

    override fun toString(): String = value
}