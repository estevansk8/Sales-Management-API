package com.api.sales_management.domain.repository

import com.api.sales_management.domain.model.SaleItem
import com.api.sales_management.domain.model.SaleItemId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleItemRepository : JpaRepository<SaleItem, SaleItemId> {
    fun findById_SaleId(saleId: Long): List<SaleItem>
}