package com.api.sales_management.client.controller

import com.api.sales_management.client.model.ClientDTO
import com.api.sales_management.client.service.ClientService
import org.springframework.http.HttpStatus
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
class ClientController(private val service: ClientService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody client: ClientDTO): ClientDTO = service.create(client)

    @GetMapping
    fun listAll(): List<ClientDTO> = service.listAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ClientDTO = service.getById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody client: ClientDTO): ClientDTO = service.update(id, client)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = service.delete(id)
}