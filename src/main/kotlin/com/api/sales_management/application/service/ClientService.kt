package com.api.sales_management.application.service

import com.api.sales_management.application.dto.request.client.ClientRequestDTO
import com.api.sales_management.application.dto.response.client.ClientResponseDTO
import com.api.sales_management.application.mapper.ClientMapper
import com.api.sales_management.domain.repository.AuthUserRepository
import com.api.sales_management.domain.repository.ClientRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Service
class ClientService(
    private val clientRepository: ClientRepository,
    private val authUserRepository: AuthUserRepository,
    private val clientMapper: ClientMapper
) {

    @Transactional
    fun createClient(requestDTO: ClientRequestDTO, authenticatedUserId: Long): ClientResponseDTO {
        val user = authUserRepository.findById(authenticatedUserId) // Usa o ID do usuário autenticado
            .orElseThrow { EntityNotFoundException("User not found with ID: $authenticatedUserId to associate with client") }

        val clientEntity = clientMapper.toEntity(requestDTO, user)
        val savedClient = clientRepository.save(clientEntity)
        return clientMapper.toResponseDTO(savedClient)
    }

    @Transactional(readOnly = true)
    fun getClientById(id: Long): ClientResponseDTO {
        val client = clientRepository.findByIdWithUser(id)
            ?: throw EntityNotFoundException("Client not found with ID: $id")
        return clientMapper.toResponseDTO(client)
    }

    @Transactional(readOnly = true)
    fun getAllClients(): List<ClientResponseDTO> {
        return clientRepository.findAll().map { clientMapper.toResponseDTO(it) }
    }

    @Transactional
    fun updateClient(id: Long, requestDTO: ClientRequestDTO): ClientResponseDTO {
        val existingClient = clientRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Client not found with ID: $id for update") }

        //               TODO: Endpoint dedicado ao update
        // A lógica para buscar/atualizar o 'user' com base no 'requestDTO.userId' foi removida.
        // O usuário associado ao cliente não será alterado por este método.

        clientMapper.updateEntityFromDTO(existingClient, requestDTO) // Não passa mais 'user'
        val updatedClient = clientRepository.save(existingClient)
        return clientMapper.toResponseDTO(updatedClient)
    }

    @Transactional
    fun deleteClient(id: Long) {
        if (!clientRepository.existsById(id)) {
            throw EntityNotFoundException("Client not found with ID: $id for deletion")
        }
        //TODO: Adicionar lógica de verificação antes de deletar (ex: se o cliente tem vendas ativas)
        clientRepository.deleteById(id)
    }
}