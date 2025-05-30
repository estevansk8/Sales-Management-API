package com.api.sales_management.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class SaleItemId(
    @Column(name = "id_sale")
    var saleId: Long = 0,

    @Column(name = "id_product")
    var productId: Long = 0
) : Serializable