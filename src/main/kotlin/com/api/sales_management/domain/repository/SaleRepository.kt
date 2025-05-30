package com.api.sales_management.domain.repository

import com.api.sales_management.domain.model.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SaleRepository : JpaRepository<Sale, Long> {
    // Busca vendas de um usuário específico com seus itens, cliente e usuário associado
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.items si LEFT JOIN FETCH si.product JOIN FETCH s.client JOIN FETCH s.user WHERE s.user.id = :userId ORDER BY s.saleDate DESC")
    fun findFullSalesByUserId(userId: Long): List<Sale>

    // Busca uma venda específica de um usuário com todos os detalhes
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.items si LEFT JOIN FETCH si.product JOIN FETCH s.client JOIN FETCH s.user WHERE s.id = :id AND s.user.id = :userId")
    fun findFullSaleByIdAndUserId(id: Long, userId: Long): Sale?

    // Se precisar de uma busca simples por ID e usuário sem todos os joins:
    fun findByIdAndUser_Id(id: Long, userId: Long): Sale?
}