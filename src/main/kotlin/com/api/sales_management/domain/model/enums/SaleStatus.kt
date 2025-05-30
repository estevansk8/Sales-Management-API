package com.api.sales_management.domain.model.enums

enum class SaleStatus {
    PENDING,    // Pedido realizado, aguardando pagamento/processamento
    PAID,       // Pago
    PROCESSING, // Em processamento/separação
    SHIPPED,    // Enviado
    DELIVERED,  // Entregue
    CANCELLED,  // Cancelado
    REFUNDED    // Devolvido/Reembolsado
}