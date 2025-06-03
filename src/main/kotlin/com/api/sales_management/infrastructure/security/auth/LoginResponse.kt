package com.api.sales_management.infrastructure.security.auth

data class LoginResponse(
    val username : String,
    val accessToken: String,
    val refreshToken: String
)
