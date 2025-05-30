package com.api.sales_management.domain.model

import com.api.sales_management.domain.model.enums.SaleStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "sale")
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", referencedColumnName = "id_client", nullable = false)
    var client: Client,

    @Column(name = "sale_date", nullable = false)
    var saleDate: LocalDate,

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    var totalAmount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: SaleStatus,

    @Column(name = "due_date")
    var dueDate: LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    var user: AuthUser,

    @OneToMany(
        mappedBy = "sale",
        cascade = [CascadeType.ALL], //Persiste/deleta itens junto com a venda
        orphanRemoval = true //Remove itens que não estão mais associados
    )
    var items: MutableList<SaleItem> = mutableListOf(),

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    // Função utilitária para adicionar item e garantir a relação bidirecional
    fun addItem(item: SaleItem) {
        items.add(item)
        item.sale = this // Garante a referência bidirecional
        // O SaleItemId do item precisará ser atualizado com o ID desta venda após ela ser persistida.
    }

    fun calculateTotalAmount() {
        this.totalAmount = items.sumOf { it.unitPrice.multiply(BigDecimal(it.quantity)) }
    }
}