package com.algonix.dto

import CategoryResponseDto

data class ProblemResponseDto(
    val id: Long,
    val title: String,
    val category: CategoryResponseDto,
    val restrictions: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    val description: String,
    val inputDescription: String,
    val outputDescription: String,
    val hints: String?,
    val difficulty: Int,
    val similarProblems: List<ProblemResponseDto>, // 비슷한 문제들의 리스트
    val authorId: Long,
    val examples: List<ExampleResponseDto>  // 여러 입력/출력 예제 응답용 DTO 리스트
)
