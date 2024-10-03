package com.algonix.dto

data class ProblemDTO(
    val title: String,
    val categoryId: Long,  // 분류 ID
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
    val examples: List<ExampleDTO>  // 여러 입력/출력 예제 DTO 리스트로 받아서 처리
)

data class ExampleDTO(
    val inputExample: String,
    val outputExample: String
)
