package com.api.sales_management.infrastructure.security.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtService(
    @Value("JWT_SECRET_BASE64") private val jwtSecret: String

) {

}