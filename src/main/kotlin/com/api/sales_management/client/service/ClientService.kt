package com.api.sales_management.client.service

import com.api.sales_management.client.domain.Client
import com.api.sales_management.client.model.ClientDTO
import com.api.sales_management.client.repository.ClientRepository
import org.springframework.stereotype.Service

@Service
class ClientService(private val repository: ClientRepository) {

    fun create(client: ClientDTO): ClientDTO {
        val entity = Client(
            nome = client.nome,
            telefone = client.telefone,
            endereco = client.endereco
        )
        return repository.save(entity).toDTO()
    }

    fun listAll(): List<ClientDTO> = repository.findAll().map { it.toDTO() }

    fun getById(id: Long): ClientDTO = repository.findById(id).orElseThrow().toDTO()

    fun delete(id: Long) = repository.deleteById(id)

    fun update(id: Long, updated: ClientDTO): ClientDTO {
        val entity = repository.findById(id).orElseThrow().copy(
            nome = updated.nome,
            telefone = updated.telefone,
            endereco = updated.endereco
        )
        return repository.save(entity).toDTO()
    }

    private fun Client.toDTO() = ClientDTO(idCliente, nome, telefone, endereco)
}