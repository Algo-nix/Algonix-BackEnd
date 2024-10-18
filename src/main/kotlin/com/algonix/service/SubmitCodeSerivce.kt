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

/**
 * TODO: Python을 제외한 다른 언어(C, Java, C++, C#, JS(NodeJS), Go, Kotlin) 실행 테스트 해야 함
 */
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

        /**
         * TODO: 실행 결과 값 return 받은 뒤 TestCase와 일치하는지, 시간 초과가 발생했는지, 메모리 초과가 발생했는지 검증 로직 추가 해야 함
         */
        val savedSubmitCode = submitCodeRepository.save(submitCode)

        val docker = Docker()
        val imageName = "${submitCodeRequestDto.language}-app"
        val codeNumber = submitCodeRequestDto.language

        // 각 example에 대해 Docker 컨테이너 실행 후 결과와 outputExample 비교
        val results = problem.examples.map { example ->
            try {
                val result = docker.runContainerWithInput(
                    imageName = imageName,
                    input = example.inputExample,
                    code = submitCodeRequestDto.code,
                    codeNumber = codeNumber,
                    executeCommand = language.executeCommand,
                    filename = language.executeFileName
                )
                result to (result == example.outputExample) // 결과와 일치 여부를 Pair로 반환
            } catch (e: Exception) {
                "Error: ${e.message}" to false
            }
        }

        // 각 결과와 예상 결과의 일치 여부 출력
        var isAllMatch = true
        results.forEachIndexed { index, (result, isMatch) ->
            println("Test Case ${index + 1}: Result: $result, Expected: ${problem.examples[index].outputExample}, Match: $isMatch")
            if (result != problem.examples[index].outputExample) {
                isAllMatch = false
            }
        }

        if (isAllMatch) {
            savedSubmitCode.status = SubmitStatus.CORRECT
            return "정답입니다."
        } else {
            savedSubmitCode.status = SubmitStatus.INCORRECT
            return "오답입니다."
        }
    }
}
