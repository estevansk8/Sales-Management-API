package com.api.sales_management.domain.model

import com.api.sales_management.domain.model.valueobject.EmailVO
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "auth_user")
data class AuthUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    val id: Long = 0,

    @Column(name = "name", length = 100)
    var name: String,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email", unique = true, length = 100))
    var email: EmailVO,

    @Column(name = "password", length = 100)
    var password: String,

    @Column(name = "profile_picture", length = 255)
    var profilePicture: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
     fun changePassword(newPasswordHash: String) {
         this.password = newPasswordHash
     }

     fun updateProfile(newName: String, newProfilePicture: String?) {
         this.name = newName
         this.profilePicture = newProfilePicture
     }
}