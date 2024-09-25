package com.algonix.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class SignupRequest(
    @NotEmpty(message = "아이디는 필수입니다.")
    val username: String,

    @NotEmpty(message = "비밀번호는 필수입니다.")
    val password: String,

    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotEmpty(message = "이메일은 필수입니다.")
    val email: String
    // TODO: 최소 비밀번호 자리 수 확인 로직 추가 필요
)
