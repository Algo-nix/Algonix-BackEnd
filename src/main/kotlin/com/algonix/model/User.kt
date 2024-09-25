package com.algonix.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    @NotEmpty(message = "아이디는 필수입니다.")
    val username: String,

    @NotEmpty(message = "비밀번호는 필수입니다.")
    var password: String,

    @Email
    @NotEmpty(message = "이메일은 필수입니다.")
    val email: String
)