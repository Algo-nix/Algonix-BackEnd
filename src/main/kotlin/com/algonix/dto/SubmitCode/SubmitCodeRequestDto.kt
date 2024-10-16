package com.algonix.dto.SubmitCode

data class SubmitCodeRequestDto(
    val language: Long, // 제출 언어
    val code: String, // 제출 코드
    val problem: Long // 문제 아이디
)
