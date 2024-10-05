package com.algonix.dto

data class ProblemDTO(
    val title: String,
    val categoryIds: Set<Long>,  // 여러 카테고리 ID
    val restrictions: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    val description: String,
    val inputDescription: String,
    val outputDescription: String,
    val hints: String? = null,
    val difficulty: Int,
    val similarProblemIds: Set<Long>,
    val authorId: Long,
    val languageIds: Set<Long>,  // 사용 가능한 언어 ID 리스트
    val examples: List<ExampleDto>  // 여러 입력/출력 예제 DTO 리스트
)
