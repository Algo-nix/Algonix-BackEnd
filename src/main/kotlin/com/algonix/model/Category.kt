package com.algonix.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null
)
