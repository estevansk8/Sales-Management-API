package com.api.sales_management.domain.repository

import com.api.sales_management.domain.model.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserRepository : JpaRepository<AuthUser, Long>{
    fun findByEmail(email: String): AuthUser?
    fun existsByEmail(email: String): Boolean
}