package com.algonix.dto.Auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class SignupRequestDto(
    @NotEmpty(message = "아이디는 필수입니다.")
    val username: String,

    @NotEmpty(message = "비밀번호는 필수입니다.")
    val password: String,

    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotEmpty(message = "이메일은 필수입니다.")
    val email: String,

    @NotEmpty(message = "닉네임은 필수입니다.")
    val nickname: String,

    @NotEmpty(message = "소속은 필수입니다.")
    val organization: String,

    @NotEmpty(message = "소속 공개 여부를 선택해 주세요.")
    val visibility: String,  // "공개" 또는 "비공개"

    val statusMessage: String? = null  // 상태 메시지 필드 추가 (선택 사항)
)
