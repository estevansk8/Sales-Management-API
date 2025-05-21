package com.api.sales_management.domain.model.valueobject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Embeddable
class EmailVO() {

    @Column(name = "email", nullable = false, unique = true)
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email should be valid")
    lateinit var value: String
        private set

    constructor(value: String) : this() {
        require(value.contains("@")) { "Invalid email address: $value" }
        this.value = value
    }
}
