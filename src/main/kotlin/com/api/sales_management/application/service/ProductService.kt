package com.api.sales_management.application.service

import com.api.sales_management.application.dto.request.product.ProductRequestDTO
import com.api.sales_management.application.dto.response.product.ProductResponseDTO
import com.api.sales_management.application.mapper.ProductMapper
import com.api.sales_management.domain.repository.AuthUserRepository
import com.api.sales_management.domain.repository.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val authUserRepository: AuthUserRepository,
    private val productMapper: ProductMapper
) {

    @Transactional
    fun createProduct(requestDTO: ProductRequestDTO, authenticatedUserId: Long): ProductResponseDTO {
        val user = authUserRepository.findById(authenticatedUserId)
            .orElseThrow { EntityNotFoundException("User not found with ID: $authenticatedUserId to associate with product") }

        val productEntity = productMapper.toEntity(requestDTO, user)
        val savedProduct = productRepository.save(productEntity)
        return productMapper.toResponseDTO(savedProduct)
    }

    @Transactional(readOnly = true)
    fun getProductById(id: Long): ProductResponseDTO {
        val product = productRepository.findByIdWithUser(id) // Usando findByIdWithUser para carregar o usuário
            ?: throw EntityNotFoundException("Product not found with ID: $id")

        // TODO: Adicionar authenticatedUserId
        // val product = productRepository.findByIdAndUser_Id(id, authenticatedUserId)
        //     ?: throw EntityNotFoundException("Product not found with ID: $id for current user")
        return productMapper.toResponseDTO(product)
    }

    @Transactional(readOnly = true)
    fun getAllProductsBydUser(authenticatedUserId: Long): List<ProductResponseDTO> {
        val products = productRepository.findByUser_Id(authenticatedUserId)
        return products.map { productMapper.toResponseDTO(it) }
    }

    @Transactional
    fun updateProduct(
        productId: Long,
        requestDTO: ProductRequestDTO,
        authenticatedUserId: Long
    ): ProductResponseDTO {
        val existingProduct = productRepository.findById(productId)
            .orElseThrow { EntityNotFoundException("Product not found with ID: $productId for update") }

        if (existingProduct.user.id != authenticatedUserId) {
            throw AccessDeniedException("User not authorized to update product with ID: $productId")
        }

        productMapper.updateEntityFromDTO(existingProduct, requestDTO)
        val updatedProduct = productRepository.save(existingProduct)
        return productMapper.toResponseDTO(updatedProduct)
    }

    @Transactional
    fun deleteProduct(productId: Long, authenticatedUserId: Long) {
        val productToDelete = productRepository.findById(productId)
            .orElseThrow { EntityNotFoundException("Product not found with ID: $productId for deletion") }

        if (productToDelete.user.id != authenticatedUserId) {
            throw AccessDeniedException("User not authorized to delete product with ID: $productId")
        }

        // if (!productRepository.existsByIdAndUser_Id(productId, authenticatedUserId)) {
        //    throw EntityNotFoundException("Product not found with ID: $productId for current user or user not authorized.")
        // }
        // productRepository.deleteById(productId)

        productRepository.delete(productToDelete)
    }
}
