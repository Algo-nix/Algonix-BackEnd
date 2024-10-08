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
    val email: String,

    @NotEmpty(message = "닉네임은 필수입니다.")
    val nickname: String,

    @NotEmpty(message = "소속은 필수입니다.")
    val organization: String,

    @NotEmpty(message = "소속 공개 여부를 선택해 주세요.")
    val visibility: String,  // "공개" 또는 "비공개"로 설정 가능

    val statusMessage: String? = null,  // 상태 메시지는 선택 사항

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,  // 기본값으로 일반 사용자 설정

    var xp: Int = 0  // 사용자 경험치 초기값은 0
)
