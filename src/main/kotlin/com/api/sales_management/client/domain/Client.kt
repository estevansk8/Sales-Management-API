package com.api.sales_management.client.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cliente")
data class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idCliente: Long = 0,

    @Column(nullable = false)
    val nome: String,

    val telefone: String?,

    val endereco: String?
)

