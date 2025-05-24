package com.api.sales_management.infrastructure.security.auth

import com.api.sales_management.application.dto.ApiResponseDTO
import com.api.sales_management.infrastructure.security.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/login")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping
    fun login(@RequestBody body: AuthRequest):  ResponseEntity<ApiResponseDTO<TokenResponse>>{
        val userLogin = authService.login(body.email, body.password)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponseDTO(true,"Login Sucess!", userLogin))
    }

}