package com.algonix.service

import com.algonix.dto.SubmitCode.SubmitCodeRequestDto
import com.algonix.model.SubmitCode
import com.algonix.model.SubmitStatus
import com.algonix.repository.ProblemRepository
import com.algonix.repository.SubmitCodeRepository
import com.algonix.repository.AvailableLanguageRepository
import com.algonix.util.Docker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubmitCodeService(
    private val problemRepository: ProblemRepository,
    private val submitCodeRepository: SubmitCodeRepository,
    private val availableLanguageRepository: AvailableLanguageRepository,
) {
    @Transactional
    fun createSubmitCode(submitCodeRequestDto: SubmitCodeRequestDto, authorId: Long): String {
        val language = availableLanguageRepository.findById(submitCodeRequestDto.language)
            .orElseThrow { IllegalArgumentException("유효하지 않은 언어 ID입니다.") }

        val problem = problemRepository.findById(submitCodeRequestDto.problem)
            .orElseThrow { IllegalArgumentException("유효하지 않은 문제 ID입니다.") }

        val submitCode = SubmitCode(
            language = submitCodeRequestDto.language,
            authorId = authorId,
            code = submitCodeRequestDto.code,
            status = SubmitStatus.SCORING,
            problem = submitCodeRequestDto.problem
        )

        val savedSubmitCode = submitCodeRepository.save(submitCode)

        val docker = Docker()
        val imageName = "${submitCodeRequestDto.language}-app"
        val codeNumber = submitCodeRequestDto.language

        val results = problem.examples.map { example ->
            try {
                val result = docker.runContainerWithInput(
                    imageName = imageName,
                    input = example.inputExample,
                    code = submitCodeRequestDto.code,
                    codeNumber = codeNumber
                )
                result
            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }

        results.forEach { result -> println(result) }

        return "정상"
    }
}
