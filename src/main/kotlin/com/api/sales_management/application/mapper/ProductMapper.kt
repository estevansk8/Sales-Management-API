package com.api.sales_management.application.mapper

import com.api.sales_management.application.dto.request.product.ProductRequestDTO
import com.api.sales_management.application.dto.response.product.ProductResponseDTO
import com.api.sales_management.domain.model.AuthUser
import com.api.sales_management.domain.model.Product
import org.springframework.stereotype.Component

@Component
class ProductMapper {

    fun toEntity(dto: ProductRequestDTO, user: AuthUser): Product {
        return Product(
            name = dto.name,
            price = dto.price,
            stock = dto.stock,
            user = user
        )
    }

    fun toResponseDTO(entity: Product): ProductResponseDTO {
        return ProductResponseDTO(
            id = entity.id,
            name = entity.name,
            price = entity.price,
            stock = entity.stock,
            userId = entity.user.id,
            userName = entity.user.name,
            createdAt = entity.createdAt
        )
    }

    fun updateEntityFromDTO(entity: Product, dto: ProductRequestDTO) {
        entity.name = dto.name
        entity.price = dto.price
        entity.stock = dto.stock
    }
}