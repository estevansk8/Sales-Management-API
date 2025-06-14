package com.api.sales_management.domain.repository

import com.api.sales_management.domain.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByUser_Id(userId: Long): List<Product>

    fun findByIdAndUser_Id(id: Long, userId: Long): Product?

    fun existsByIdAndUser_Id(id: Long, userId: Long): Boolean

    fun findByNameContainingIgnoreCase(name: String): List<Product>

    @Query("SELECT p FROM Product p JOIN FETCH p.user WHERE p.id = :id")
    fun findByIdWithUser(id: Long): Product?
}