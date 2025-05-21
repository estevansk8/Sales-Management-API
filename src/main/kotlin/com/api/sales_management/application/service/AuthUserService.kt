package com.api.sales_management.application.service

import com.api.sales_management.application.dto.request.user.UserCreateRequestDTO
import com.api.sales_management.application.dto.request.user.UserUpdateRequestDTO
import com.api.sales_management.application.dto.response.user.UserResponseDTO
import com.api.sales_management.application.mapper.AuthUserMapper
import com.api.sales_management.domain.repository.AuthUserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class AuthUserService(
    private val userRepository: AuthUserRepository,
    private val userMapper: AuthUserMapper,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createUser(requestDTO: UserCreateRequestDTO): UserResponseDTO {
        if (userRepository.existsByEmail(requestDTO.email)) {
            throw IllegalArgumentException("Email already registered: ${requestDTO.email.value}")
        }
        val hashedPassword = passwordEncoder.encode(requestDTO.password)
        val user = userMapper.toEntity(requestDTO).copy(password = hashedPassword)
        val savedUser = userRepository.save(user)
        return userMapper.toResponseDTO(savedUser)
    }

    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserResponseDTO {
        val user = userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found with ID: $id") }
        return userMapper.toResponseDTO(user)
    }

    @Transactional(readOnly = true)
    fun getAllUsers(): List<UserResponseDTO> {
        return userRepository.findAll().map { userMapper.toResponseDTO(it) }
    }

    @Transactional
    fun updateUser(id: Long, requestDTO: UserUpdateRequestDTO): UserResponseDTO {
        val existingUser = userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found with ID: $id") }

        requestDTO.email?.let { newEmail ->
            if (newEmail.value != existingUser.email.value && userRepository.existsByEmail(newEmail)) {
                throw IllegalArgumentException("New email already registered: ${newEmail.value}")
            }
        }

        userMapper.updateEntityFromDTO(existingUser, requestDTO)
        requestDTO.password?.let {
            existingUser.password = passwordEncoder.encode(it)
        }

        val updatedUser = userRepository.save(existingUser)
        return userMapper.toResponseDTO(updatedUser)
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw EntityNotFoundException("User not found with ID: $id for deletion")
        }
        //TODO: Adicionar lógica aqui para verificar se o usuário pode ser deletado
        // (ex: não tem clientes ou vendas associadas que impeçam a deleção)
        userRepository.deleteById(id)
    }
}