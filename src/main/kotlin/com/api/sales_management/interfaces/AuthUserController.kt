package com.api.sales_management.interfaces

import com.api.sales_management.application.dto.ApiResponseDTO
import com.api.sales_management.application.dto.request.user.UserCreateRequestDTO
import com.api.sales_management.application.dto.request.user.UserUpdateRequestDTO
import com.api.sales_management.application.dto.response.user.UserResponseDTO
import com.api.sales_management.application.service.AuthUserService
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
@RequestMapping("/api/v1/user")
class AuthUserController(
    private val authUserService: AuthUserService
) {

    @PostMapping
    fun createUser(@Valid @RequestBody userCreateDTO: UserCreateRequestDTO): ResponseEntity<ApiResponseDTO<UserResponseDTO>> {
        print(userCreateDTO)
        val createdUser = authUserService.createUser(userCreateDTO)
        print("CreatedUser: $createdUser")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO(true, "User created successfully", createdUser))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<UserResponseDTO>> {
        val user = authUserService.getUserById(id)
        return ResponseEntity.ok(ApiResponseDTO(true, "User retrieved successfully", user))
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> {
        val users = authUserService.getAllUsers()
        return ResponseEntity.ok(ApiResponseDTO(true, "Users retrieved successfully", users))
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody userUpdateDTO: UserUpdateRequestDTO
    ): ResponseEntity<ApiResponseDTO<UserResponseDTO>> {
        val updatedUser = authUserService.updateUser(id, userUpdateDTO)
        return ResponseEntity.ok(ApiResponseDTO(true, "User updated successfully", updatedUser))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ApiResponseDTO<Unit>> {
        authUserService.deleteUser(id)
        return ResponseEntity
            .ok(ApiResponseDTO(true, "User with ID:$id deleted successfully"))
    }
}