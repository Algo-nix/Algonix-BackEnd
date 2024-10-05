package com.algonix.service

import com.algonix.dto.Category.CategoryResponseDto
import com.algonix.dto.AvailableLanguage.LanguageResponseDto
import com.algonix.dto.Example.ExampleDto
import com.algonix.dto.Problem.ProblemDTO
import com.algonix.dto.Problem.ProblemResponseDto
import com.algonix.model.Example
import com.algonix.model.Problem
import com.algonix.repository.AvailableLanguageRepository
import com.algonix.repository.CategoryRepository
import com.algonix.repository.ExampleRepository
import com.algonix.repository.ProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProblemService(
    private val problemRepository: ProblemRepository,
    private val categoryRepository: CategoryRepository,
    private val exampleRepository: ExampleRepository,
    private val availableLanguageRepository: AvailableLanguageRepository
) {

    @Transactional
    fun createProblem(problemDTO: ProblemDTO, authorId: Long): ProblemResponseDto {
        // 카테고리 조회
        val categories = categoryRepository.findAllById(problemDTO.categoryIds).toSet()
        if (categories.isEmpty()) {
            throw IllegalArgumentException("유효하지 않은 카테고리 ID입니다.")
        }

        // 사용 가능한 언어 조회
        val availableLanguages = availableLanguageRepository.findAllById(problemDTO.languageIds).toSet()
        if (availableLanguages.isEmpty()) {
            throw IllegalArgumentException("유효하지 않은 언어 ID입니다.")
        }

        // 문제 생성
        val problem = Problem(
            title = problemDTO.title,
            categories = categories,
            restrictions = problemDTO.restrictions,
            timeLimit = problemDTO.timeLimit,
            memoryLimit = problemDTO.memoryLimit,
            description = problemDTO.description,
            inputDescription = problemDTO.inputDescription,
            outputDescription = problemDTO.outputDescription,
            hints = problemDTO.hints,
            difficulty = problemDTO.difficulty,
            authorId = authorId,  // JWT에서 가져온 사용자 ID를 사용
            availableLanguages = availableLanguages
        )

        // 문제 저장
        val savedProblem = problemRepository.save(problem)

        // 예제 저장
        problemDTO.examples.forEach { exampleDTO ->
            val example = Example(
                problem = savedProblem,
                inputExample = exampleDTO.inputExample,
                outputExample = exampleDTO.outputExample
            )
            exampleRepository.save(example)
        }

        return toProblemResponseDto(savedProblem)
    }

    // Problem을 ProblemResponseDto로 변환하는 메소드
    private fun toProblemResponseDto(problem: Problem): ProblemResponseDto {
        val examples = exampleRepository.findByProblemId(problem.id).map { example ->
            ExampleDto(
                inputExample = example.inputExample,
                outputExample = example.outputExample
            )
        }

        // ProblemResponseDto에 사용 가능한 언어 추가
        val availableLanguages = problem.availableLanguages.map { language ->
            LanguageResponseDto(
                id = language.id,
                name = language.name,
                compileCommand = language.compileCommand,
                executeCommand = language.executeCommand,
                version = language.version,
                exampleCode = language.exampleCode
            )
        }

        return ProblemResponseDto(
            id = problem.id,
            title = problem.title,
            categories = problem.categories.map { category ->
                CategoryResponseDto(
                    id = category.id,
                    name = category.name
                )
            },
            restrictions = problem.restrictions,
            timeLimit = problem.timeLimit,
            memoryLimit = problem.memoryLimit,
            description = problem.description,
            inputDescription = problem.inputDescription,
            outputDescription = problem.outputDescription,
            hints = problem.hints,
            difficulty = problem.difficulty,
            similarProblems = listOf(),  // TODO: 유사 문제 구현
            authorId = problem.authorId,
            availableLanguages = availableLanguages, // 사용 가능한 언어들 추가
            examples = examples
        )
    }

    // 문제 ID로 문제 조회
    fun getProblemById(id: Long): ProblemResponseDto {
        val problem = problemRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 문제를 찾을 수 없습니다.")
        }
        return toProblemResponseDto(problem)
    }
}
