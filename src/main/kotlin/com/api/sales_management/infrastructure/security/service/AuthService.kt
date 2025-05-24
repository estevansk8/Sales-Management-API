package com.api.sales_management.infrastructure.security.service

import com.api.sales_management.domain.model.AuthUser
import com.api.sales_management.domain.repository.AuthUserRepository
import com.api.sales_management.infrastructure.security.auth.TokenResponse
import com.api.sales_management.infrastructure.security.config.HashEncoder
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.server.ResponseStatusException
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: AuthUserRepository,
    private val hashEncoder: HashEncoder,
) {
    fun login(email: String, password: String): TokenResponse {
        val user = userRepository.findByEmail(email)
            ?: throw BadCredentialsException("Invalid credentials.")

        if(!hashEncoder.matches(password, user.password)) {
            throw BadCredentialsException("Invalid credentials.")
        }

        val newAccessToken = jwtService.generateAccessToken(user.id.toString())
        val newRefreshToken = jwtService.generateRefreshToken(user.id.toString())

//        storeRefreshToken(user, newRefreshToken)

        return TokenResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

//    @Transactional
//    fun refresh(refreshToken: String): TokenResponse {
//        if(!jwtService.validateRefreshToken(refreshToken)) {
//            throw ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
//        }
//
//        val userId = jwtService.getUserIdFromToken(refreshToken)
//        val user = userRepository.findById(userId.toLong()).orElseThrow {
//            ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.")
//        }
//
//        val hashed = hashToken(refreshToken)
//        refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
//            ?: throw ResponseStatusException(
//                HttpStatusCode.valueOf(401),
//                "Refresh token not recognized (maybe used or expired?)"
//            )
//
//        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)
//
//        val newAccessToken = jwtService.generateAccessToken(userId)
//        val newRefreshToken = jwtService.generateRefreshToken(userId)
//
//        storeRefreshToken(user, newRefreshToken)
//
//        return TokenResponse(
//            accessToken = newAccessToken,
//            refreshToken = newRefreshToken
//        )
//    }
//
//    private fun storeRefreshToken(user: AuthUser, rawRefreshToken: String) {
//        val hashed = hashToken(rawRefreshToken)
//        val expiryMs = jwtService.refreshTokenValidityMs
//        val expiresAt = Instant.now().plusMillis(expiryMs)
//
//        refreshTokenRepository.save(
//            RefreshToken(
//                userId = userId,
//                expiresAt = expiresAt,
//                hashedToken = hashed
//            )
//        )
//    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }

}