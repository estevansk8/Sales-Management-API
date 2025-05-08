package com.api.sales_management.client.model

data class ClientDTO(
    val id: Long? = null,
    val name: String,
    val phone: String?,
    val address: String?
)
