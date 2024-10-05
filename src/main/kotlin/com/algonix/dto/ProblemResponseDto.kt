package com.algonix.dto

import CategoryResponseDto

// 문제 응답에 사용되는 DTO
data class ProblemResponseDto(
    val id: Long,
    val title: String,
    val categories: List<CategoryResponseDto>,  // 여러 카테고리
    val restrictions: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    val description: String,
    val inputDescription: String,
    val outputDescription: String,
    val hints: String?,
    val difficulty: Int,
    val similarProblems: List<SimilarProblemDto>,  // 비슷한 문제들
    val authorId: Long,
    val availableLanguages: List<LanguageResponseDto>,  // 사용 가능한 언어들
    val examples: List<ExampleDto>  // 입력/출력 예제들
)
