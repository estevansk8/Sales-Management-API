package com.api.sales_management.domain.model

import com.api.sales_management.domain.model.AuthUser
import com.api.sales_management.domain.model.valueobject.AddressVO
import com.api.sales_management.domain.model.valueobject.PhoneVO
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "client")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    val id: Long = 0,

    @Column(name = "name", length = 100, nullable = false)
    var name: String,

    @Column(name = "phone", length = 20, nullable = false)
    var phone: String?,

    @Column(name = "address", length = 200, nullable = false)
    var address: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    var user: AuthUser,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

     fun updateContactInfo(newPhone: String?, newAddress: String?) {
         this.phone = newPhone
         this.address = newAddress
     }
}