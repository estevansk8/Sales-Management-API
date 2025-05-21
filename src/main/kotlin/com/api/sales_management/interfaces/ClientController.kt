package com.api.sales_management.interfaces

import com.api.sales_management.application.dto.ApiResponseDTO
import com.api.sales_management.application.dto.request.client.ClientRequestDTO
import com.api.sales_management.application.dto.response.client.ClientResponseDTO
import com.api.sales_management.application.service.ClientService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/clients")
class ClientController(
    private val clientService: ClientService
) {

    @PostMapping
    fun createClient(@Valid @RequestBody clientRequestDTO: ClientRequestDTO): ResponseEntity<ApiResponseDTO<ClientResponseDTO>> {
        val createdClient = clientService.createClient(clientRequestDTO)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO(true, "Client created successfully", createdClient))
    }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<ClientResponseDTO>> {
        val client = clientService.getClientById(id)
        return ResponseEntity.ok(ApiResponseDTO(true, "Client retrieved successfully", client))
    }

    @GetMapping
    fun getAllClients(): ResponseEntity<ApiResponseDTO<List<ClientResponseDTO>>> {
        val clients = clientService.getAllClients()
        return ResponseEntity.ok(ApiResponseDTO(true, "Clients retrieved successfully", clients))
    }

    @PutMapping("/{id}")
    fun updateClient(
        @PathVariable id: Long,
        @Valid @RequestBody clientRequestDTO: ClientRequestDTO
    ): ResponseEntity<ApiResponseDTO<ClientResponseDTO>> {
        val updatedClient = clientService.updateClient(id, clientRequestDTO)
        return ResponseEntity.ok(ApiResponseDTO(true, "Client '${updatedClient.name}' updated successfully", updatedClient))
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<Unit>> {
        clientService.deleteClient(id)
        return ResponseEntity.ok(ApiResponseDTO(true, "Client with ID:$id deleted successfully"))
    }
}