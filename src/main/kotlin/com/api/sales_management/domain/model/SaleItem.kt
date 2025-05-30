package com.api.sales_management.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "sale_item")
data class SaleItem(
    @EmbeddedId
    var id: SaleItemId = SaleItemId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("saleId")
    @JoinColumn(name = "id_sale", insertable = false, updatable = false)
    var sale: Sale,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    var product: Product,

    @Column(nullable = false)
    var quantity: Int,

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    var unitPrice: BigDecimal,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor(sale: Sale, product: Product, quantity: Int, unitPrice: BigDecimal) : this(
        id = SaleItemId(sale.id, product.id), // O ID da venda pode não estar disponível aqui se a venda não foi persistida
        sale = sale,
        product = product,
        quantity = quantity,
        unitPrice = unitPrice
    )
}