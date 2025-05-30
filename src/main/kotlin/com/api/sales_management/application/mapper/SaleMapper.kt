package com.api.sales_management.application.mapper

import com.api.sales_management.application.dto.request.sale.SaleItemRequestDTO
import com.api.sales_management.application.dto.request.sale.SaleRequestDTO
import com.api.sales_management.application.dto.response.sale.SaleItemResponseDTO
import com.api.sales_management.application.dto.response.sale.SaleResponseDTO
import com.api.sales_management.domain.model.AuthUser
import com.api.sales_management.domain.model.Client
import com.api.sales_management.domain.model.Sale
import com.api.sales_management.domain.model.SaleItem
import com.api.sales_management.domain.model.SaleItemId
import com.api.sales_management.domain.model.enums.SaleStatus
import com.api.sales_management.domain.repository.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class SaleMapper(
    private val productRepository: ProductRepository
) {

    // --- Mapeamento de Item de Venda ---
    fun saleItemRequestToEntity(
        dto: SaleItemRequestDTO,
        sale: Sale // A venda à qual este item pertencerá
    ): SaleItem {
        val product = productRepository.findById(dto.productId)
            .orElseThrow { EntityNotFoundException("Product not found with ID: ${dto.productId}") }

        // Cria a entidade SaleItem. O SaleItemId será parcialmente preenchido aqui.
        // O sale.id será definido quando a Sale for persistida e a relação bidirecional estabelecida.
        return SaleItem(
            id = SaleItemId(saleId = 0L, productId = product.id), // saleId será atualizado depois
            sale = sale, // Associação temporária, pode precisar ser re-setada após 'sale' ter ID
            product = product,
            quantity = dto.quantity,
            // Usar o preço do DTO. Poderia ter lógica para buscar do 'product.price' se não fornecido.
            unitPrice = dto.unitPrice
        )
    }

    fun saleItemToResponseDTO(entity: SaleItem): SaleItemResponseDTO {
        return SaleItemResponseDTO(
            productId = entity.product.id,
            productName = entity.product.name,
            quantity = entity.quantity,
            unitPrice = entity.unitPrice,
            subtotal = entity.unitPrice.multiply(BigDecimal(entity.quantity))
        )
    }

    // --- Mapeamento de Venda ---
    fun saleRequestToEntity(
        dto: SaleRequestDTO,
        user: AuthUser,
        client: Client
    ): Sale {
        val saleEntity = Sale(
            client = client,
            saleDate = dto.saleDate,
            totalAmount = BigDecimal.ZERO, // Será calculado
            status = dto.status,
            dueDate = dto.dueDate,
            user = user
        )

        // Mapeia os itens. Neste ponto, saleEntity ainda não tem ID.
        // O SaleItem construtor e a lógica do serviço precisarão lidar com isso.
        val saleItems = dto.items.map { itemDto ->
            val product = productRepository.findById(itemDto.productId)
                .orElseThrow { EntityNotFoundException("Product not found with ID: ${itemDto.productId} for an item") }
            // Criamos o SaleItem, mas o SaleItemId.saleId ainda não pode ser o ID final.
            // O serviço irá gerenciar a persistência e a ligação correta.
            SaleItem(
                id = SaleItemId(saleId = 0L, productId = product.id), // saleId placeholder
                sale = saleEntity, // Associa ao objeto Sale pai
                product = product,
                quantity = itemDto.quantity,
                unitPrice = itemDto.unitPrice // Usando o preço do DTO
            )
        }
        saleEntity.items.addAll(saleItems) // Adiciona os itens mapeados
        saleEntity.calculateTotalAmount() // Calcula o total com base nos itens adicionados

        return saleEntity
    }

    fun saleToResponseDTO(entity: Sale): SaleResponseDTO {
        return SaleResponseDTO(
            id = entity.id,
            clientId = entity.client.id,
            clientName = entity.client.name,
            saleDate = entity.saleDate,
            totalAmount = entity.totalAmount,
            status = entity.status,
            dueDate = entity.dueDate,
            createdAt = entity.createdAt,
            items = entity.items.map { saleItemToResponseDTO(it) }
        )
    }

    // TODO: Adicionar mais lógica se necessário, ex: dueDate para status PENDING
    fun updateSaleStatusFromDTO(entity: Sale, newStatus: SaleStatus) {
        entity.status = newStatus
    }
}