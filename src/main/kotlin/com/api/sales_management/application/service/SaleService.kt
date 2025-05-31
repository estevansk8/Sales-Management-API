package com.api.sales_management.application.service

import com.api.sales_management.application.dto.request.sale.SaleRequestDTO
import com.api.sales_management.application.dto.response.sale.SaleResponseDTO
import com.api.sales_management.application.mapper.SaleMapper
import com.api.sales_management.domain.model.SaleItem
import com.api.sales_management.domain.model.SaleItemId
import com.api.sales_management.domain.model.enums.SaleStatus
import com.api.sales_management.domain.repository.*
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaleService(
    private val saleRepository: SaleRepository,
    private val saleItemRepository: SaleItemRepository,
    private val authUserRepository: AuthUserRepository,
    private val clientRepository: ClientRepository,
    private val productRepository: ProductRepository,
    private val saleMapper: SaleMapper
) {

    @Transactional
    fun createSale(requestDTO: SaleRequestDTO, authenticatedUserId: Long): SaleResponseDTO {
        val user = authUserRepository.findById(authenticatedUserId)
            .orElseThrow { EntityNotFoundException("User not found with ID: $authenticatedUserId") }
        val client = clientRepository.findById(requestDTO.clientId)
            .orElseThrow { EntityNotFoundException("Client not found with ID: ${requestDTO.clientId}") }

        // Mapeia o DTO para a entidade Sale. Os itens são criados com saleId temporário.
        val saleEntity = saleMapper.saleRequestToEntity(requestDTO, user, client)

        // 1. Salva a Venda primeiro para obter o ID da Venda.
        val savedSale = saleRepository.save(saleEntity)

        // 2. Agora que 'savedSale' tem um ID, atualiza/cria os SaleItems com o ID correto da Venda.
        // Limpa os itens temporários (se a cascata não os criou corretamente ou se o ID não foi propagado)
        // e recria-os com o ID da venda correto.
        // A abordagem com CascadeType.ALL e mappedBy deveria cuidar disso,
        // mas a chave composta SaleItemId pode precisar de atenção especial.


        // A maneira mais segura é limpar os itens criados pelo mapper (que não tinham o saleId correto)
        // e recriá-los agora que 'savedSale' tem um ID.
        savedSale.items.clear() // Remove os itens temporários que foram cacheados
        val finalSaleItems = requestDTO.items.map { itemDto ->
            val product = productRepository.findById(itemDto.productId)
                .orElseThrow { EntityNotFoundException("Product not found with ID: ${itemDto.productId}") }
            SaleItem(
                id = SaleItemId(saleId = savedSale.id, productId = product.id), // Agora com o saleId correto
                sale = savedSale,
                product = product,
                quantity = itemDto.quantity,
                unitPrice = itemDto.unitPrice // Usando o preço do DTO
            )
        }

        // Adiciona os itens finais à coleção da venda.
        // Como SaleItem não está em CascadePersist a partir daqui (já que esta gerenciando manualmente),
        // precisa salvar cada SaleItem individualmente se CascadeType.ALL não for suficiente
        // devido à forma como a chave composta e o ID são definidos.
        // Ou, se a cascata funcionar corretamente com a atualização do ID da venda nos itens:
        savedSale.items.addAll(finalSaleItems)
        savedSale.calculateTotalAmount() // Recalcula o total com os itens finais

        // Salva novamente a venda com os itens devidamente referenciados e o total recalculado
        // A cascata deve persistir os novos SaleItems agora que estão corretamente ligados.
        val fullySavedSale = saleRepository.save(savedSale)

        // TODO: Implementar lógica de atualização de estoque do produto
        // finalSaleItems.forEach { item ->
        //     val product = item.product
        //     product.stock -= item.quantity
        //     productRepository.save(product)
        // }

        return saleMapper.saleToResponseDTO(fullySavedSale)
    }

    @Transactional(readOnly = true)
    fun getSaleByIdForUser(saleId: Long, authenticatedUserId: Long): SaleResponseDTO {
        val sale = saleRepository.findFullSaleByIdAndUserId(saleId, authenticatedUserId)
            ?: throw EntityNotFoundException("Sale not found with ID: $saleId for current user, or access denied.")
        return saleMapper.saleToResponseDTO(sale)
    }

    @Transactional(readOnly = true)
    fun getAllSalesForUser(authenticatedUserId: Long): List<SaleResponseDTO> {
        val sales = saleRepository.findFullSalesByUserId(authenticatedUserId)
        return sales.map { saleMapper.saleToResponseDTO(it) }
    }

    @Transactional
    fun updateSaleStatus(saleId: Long, newStatus: SaleStatus, authenticatedUserId: Long): SaleResponseDTO {
        val sale = saleRepository.findByIdAndUser_Id(saleId, authenticatedUserId) // Busca simples primeiro
            ?: throw  EntityNotFoundException("Sale not found with ID: $saleId for update, or access denied.")

        // TODO: Adicionar lógica de validação de transição de status
        // Ex: não pode cancelar uma venda já entregue, etc.
        sale.status = newStatus
        if (newStatus == SaleStatus.PAID) {
            // Lógica adicional, ex: registrar pagamento
        } else if (newStatus == SaleStatus.CANCELLED) {
            // TODO: Lógica de estorno de estoque
            // sale.items.forEach { item ->
            //     val product = productRepository.findById(item.product.id).get()
            //     product.stock += item.quantity
            //     productRepository.save(product)
            // }
        }

        val updatedSale = saleRepository.save(sale)
        // Para retornar o DTO completo, pode ser necessário recarregar com todos os joins
        val fullUpdatedSale = saleRepository.findFullSaleByIdAndUserId(updatedSale.id, authenticatedUserId)!!
        return saleMapper.saleToResponseDTO(fullUpdatedSale)
    }


    @Transactional
    fun deleteSaleForUser(saleId: Long, authenticatedUserId: Long) {
        val sale = saleRepository.findByIdAndUser_Id(saleId, authenticatedUserId)
            ?: throw EntityNotFoundException("Sale not found with ID: $saleId for deletion, or access denied.")

        // TODO: Se o status for CANCELLED ou algo que precise de estorno de estoque, tratar aqui.
        // if (sale.status != SaleStatus.CANCELLED && /* outros status que não retornam estoque */) {
        //     sale.items.forEach { item ->
        //         val product = productRepository.findById(item.product.id).orElse(null)
        //         product?.let {
        //             it.stock += item.quantity
        //             productRepository.save(it)
        //         }
        //     }
        // }
        saleRepository.delete(sale) // CascadeType.ALL e orphanRemoval=true devem remover os SaleItems
    }
}
