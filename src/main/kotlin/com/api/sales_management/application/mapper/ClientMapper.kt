package com.api.sales_management.application.mapper

import com.api.sales_management.application.dto.request.client.ClientRequestDTO
import com.api.sales_management.application.dto.response.client.ClientResponseDTO
import com.api.sales_management.domain.model.AuthUser
import com.api.sales_management.domain.model.Client
import org.springframework.stereotype.Component

@Component
class ClientMapper {

    fun toEntity(dto: ClientRequestDTO, user: AuthUser): Client {
        return Client(
            name = dto.name,
            phone = dto.phone,
            address = dto.address,
            user = user
        )
    }

    fun toResponseDTO(entity: Client): ClientResponseDTO {
        return ClientResponseDTO(
            id = entity.id,
            name = entity.name,
            phone = entity.phone,
            address = entity.address,
            userId = entity.user.id,
            userName = entity.user.name,
            createdAt = entity.createdAt
        )
    }

    fun updateEntityFromDTO(entity: Client, dto: ClientRequestDTO, user: AuthUser?) {
        entity.name = dto.name
        entity.phone = dto.phone
        entity.address = dto.address
        user?.let { entity.user = it }
    }
}
