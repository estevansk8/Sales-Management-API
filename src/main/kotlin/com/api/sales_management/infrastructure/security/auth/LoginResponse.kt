package com.api.sales_management.infrastructure.security.auth

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
