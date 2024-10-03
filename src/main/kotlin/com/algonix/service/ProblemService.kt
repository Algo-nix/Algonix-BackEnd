package com.algonix.service

import CategoryResponseDto
import com.algonix.dto.ProblemDTO
import com.algonix.dto.ProblemResponseDto
import com.algonix.dto.ExampleResponseDto
import com.algonix.model.Example
import com.algonix.model.Problem
import com.algonix.repository.CategoryRepository
import com.algonix.repository.ExampleRepository
import com.algonix.repository.ProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val categoryRepository: CategoryRepository,
    private val exampleRepository: ExampleRepository
) {

    @Transactional
    fun createProblem(problemDTO: ProblemDTO): ProblemResponseDto {
        // Category 조회
        val category = categoryRepository.findById(problemDTO.categoryId)
            .orElseThrow { IllegalArgumentException("잘못된 카테고리 ID입니다.") }

        // 문제 생성
        val problem = Problem(
            title = problemDTO.title,
            category = category,
            restrictions = problemDTO.restrictions,
            timeLimit = problemDTO.timeLimit,
            memoryLimit = problemDTO.memoryLimit,
            description = problemDTO.description,
            inputDescription = problemDTO.inputDescription,
            outputDescription = problemDTO.outputDescription,
            hints = problemDTO.hints,
            difficulty = problemDTO.difficulty,
            authorId = problemDTO.authorId
        )

        // 문제 저장
        val savedProblem = problemRepository.save(problem)

        // 입력/출력 예제 저장
        problemDTO.examples.forEach { exampleDTO ->
            val example = Example(
                problem = savedProblem,
                inputExample = exampleDTO.inputExample,
                outputExample = exampleDTO.outputExample
            )
            exampleRepository.save(example)
        }

        // Response DTO 생성
        return toProblemResponseDto(savedProblem)
    }

    fun getProblemById(id: Long): ProblemResponseDto {
        val problem = problemRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 문제를 찾을 수 없습니다.")
        }
        return toProblemResponseDto(problem)
    }

    private fun toProblemResponseDto(problem: Problem): ProblemResponseDto {
        val categoryResponse = CategoryResponseDto(
            id = problem.category.id,
            name = problem.category.name,
            createdAt = problem.category.createdAt ?: LocalDateTime.now(),
            updatedAt = problem.category.updatedAt ?: LocalDateTime.now()
        )

        val examples = exampleRepository.findByProblemId(problem.id).map { example ->
            ExampleResponseDto(
                inputExample = example.inputExample,
                outputExample = example.outputExample
            )
        }

        return ProblemResponseDto(
            id = problem.id,
            title = problem.title,
            category = categoryResponse,
            restrictions = problem.restrictions,
            timeLimit = problem.timeLimit,
            memoryLimit = problem.memoryLimit,
            description = problem.description,
            inputDescription = problem.inputDescription,
            outputDescription = problem.outputDescription,
            hints = problem.hints,
            difficulty = problem.difficulty,
            similarProblems = emptyList(),  // TODO: 비슷한 문제 목록은 이후 확장
            authorId = problem.authorId,
            examples = examples
        )
    }
}
