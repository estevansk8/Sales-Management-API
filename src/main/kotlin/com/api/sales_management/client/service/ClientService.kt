package com.api.sales_management.client.service

import com.api.sales_management.client.domain.Client
import com.api.sales_management.client.model.ClientDTO
import com.api.sales_management.client.repository.ClientRepository
import org.springframework.stereotype.Service

@Service
class ClientService(private val repository: ClientRepository) {

    fun create(client: ClientDTO): ClientDTO {
        val entity = Client(
            name = client.name,
            phone = client.phone,
            address = client.address
        )
        return repository.save(entity).toDTO()
    }

    fun listAll(): List<ClientDTO> = repository.findAll().map { it.toDTO() }

    fun getById(id: Long): ClientDTO = repository.findById(id).orElseThrow().toDTO()

    fun delete(id: Long) = repository.deleteById(id)

    fun update(id: Long, updated: ClientDTO): ClientDTO {
        val entity = repository.findById(id).orElseThrow().copy(
            name = updated.name,
            phone = updated.phone,
            address = updated.address
        )
        return repository.save(entity).toDTO()
    }

    private fun Client.toDTO() = ClientDTO(idClient, name, phone, address)
}