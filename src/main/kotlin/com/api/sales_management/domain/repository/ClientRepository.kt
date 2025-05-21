package com.api.sales_management.domain.repository

import com.api.sales_management.domain.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN FETCH c.user WHERE c.id = :id")
    fun findByIdWithUser(id: Long): Client?
}