package com.api.sales_management.application.mapper

import com.api.sales_management.application.dto.request.user.UserCreateRequestDTO
import com.api.sales_management.application.dto.request.user.UserUpdateRequestDTO
import com.api.sales_management.application.dto.response.user.UserResponseDTO
import com.api.sales_management.domain.model.AuthUser
import org.springframework.stereotype.Component

@Component
class AuthUserMapper() {

    fun toEntity(dto: UserCreateRequestDTO ): AuthUser {
        return AuthUser(
            name = dto.name,
            email = dto.email,
            password = dto.password,
            profilePicture = dto.profilePicture
        )
    }

    fun toResponseDTO(entity: AuthUser): UserResponseDTO {
        return UserResponseDTO(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            profilePicture = entity.profilePicture,
            createdAt = entity.createdAt
        )
    }

    fun updateEntityFromDTO(entity: AuthUser, dto: UserUpdateRequestDTO) {
        dto.name?.let { entity.name = it }
        dto.email?.let { entity.email = it }
        dto.password?.let { entity.password = it }
        if (dto.profilePicture != null) entity.profilePicture = dto.profilePicture

    }
}
