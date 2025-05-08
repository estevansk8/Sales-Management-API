package com.api.sales_management.client.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "client")
data class Client(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idClient: Long = 0,

    @Column(nullable = false)
    val name: String,

    val phone: String?,

    val address: String?
)

