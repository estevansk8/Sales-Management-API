package com.api.sales_management.client.controller

import com.api.sales_management.client.model.ClientDTO
import com.api.sales_management.client.service.ClientService
import com.api.sales_management.shared.dto.ApiResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clients")
class ClientController(
    private val service: ClientService
) {

    @PostMapping
    fun create(@RequestBody client: ClientDTO): ResponseEntity<ApiResponseDTO<ClientDTO>> {
        val createdClient = service.create(client)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO(true, "created", createdClient))
    }

    @GetMapping
    fun listAll(): ResponseEntity<ApiResponseDTO<List<ClientDTO>>> {
        val clients = service.listAll()
        return ResponseEntity.ok(ApiResponseDTO(true, "ok", clients  ))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<ClientDTO>> {
        val client = service.getById(id)
        return ResponseEntity.ok(ApiResponseDTO(true, "ok", client))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody client: ClientDTO): ResponseEntity<ApiResponseDTO<ClientDTO>> {
        val updated = service.update(id, client)
        return ResponseEntity.ok(ApiResponseDTO(true, "${client.name} updated",updated))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<Unit>> {
        service.delete(id)
        return ResponseEntity.ok(ApiResponseDTO(true, "client id:$id deleted" ))
    }
}