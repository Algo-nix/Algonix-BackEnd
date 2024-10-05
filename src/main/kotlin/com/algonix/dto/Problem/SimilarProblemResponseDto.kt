package com.algonix.dto.Problem

// 비슷한 문제를 위한 DTO
data class SimilarProblemResponseDto(
    val id: Long,
    val title: String,
    val difficulty: Int
)