package com.api.sales_management.client.model

data class ClientDTO(
    val id: Long? = null,
    val nome: String,
    val telefone: String?,
    val endereco: String?
)
