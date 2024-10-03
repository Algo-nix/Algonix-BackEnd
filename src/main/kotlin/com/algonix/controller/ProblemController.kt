package com.algonix.controller

import com.algonix.dto.ProblemDTO
import com.algonix.dto.ProblemResponseDto
import com.algonix.security.UserDetailsImpl
import com.algonix.service.ProblemService
import com.algonix.util.Result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.Authentication

@RestController
@RequestMapping("/v1/problems")
class ProblemController(
    private val problemService: ProblemService
) {

    @PostMapping
    fun createProblem(
        @RequestBody problemDTO: ProblemDTO,
        authentication: Authentication
    ): ResponseEntity<Map<String, Any>> {
        return try {
            // JWT 토큰에서 사용자 ID 추출
            val authorId = (authentication.principal as UserDetailsImpl).id
            val problemWithAuthor = problemDTO.copy(authorId = authorId)

            val problemResponse = problemService.createProblem(problemWithAuthor)
            Result.created(problemResponse)
        } catch (e: IllegalArgumentException) {
            Result.badRequest(e.message ?: "잘못된 요청입니다.")
        } catch (e: Exception) {
            Result.internalServerError("문제 생성 중 오류가 발생했습니다.")
        }
    }

    @GetMapping("/{id}")
    fun getProblemById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        return try {
            val problemResponse = problemService.getProblemById(id)
            Result.ok(problemResponse)
        } catch (e: IllegalArgumentException) {
            Result.notFound(e.message ?: "해당 문제를 찾을 수 없습니다.")
        } catch (e: Exception) {
            Result.internalServerError("문제 조회 중 오류가 발생했습니다.")
        }
    }
}
