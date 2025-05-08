package com.api.sales_management.client.repository

import com.api.sales_management.client.domain.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<Client, Long>