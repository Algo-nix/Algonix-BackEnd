package com.algonix.controller

import com.algonix.dto.LanguageRequestDto
import com.algonix.dto.LanguageResponseDto
import com.algonix.service.AvailableLanguageService
import com.algonix.util.Result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/languages")
class AvailableLanguageController(
    private val availableLanguageService: AvailableLanguageService
) {

    @PostMapping
    fun createLanguage(@RequestBody languageRequestDto: LanguageRequestDto): ResponseEntity<Map<String, Any>> {
        return try {
            val languageResponseDto = availableLanguageService.createLanguage(languageRequestDto)
            Result.created(languageResponseDto)
        } catch (e: IllegalArgumentException) {
            Result.badRequest(e.message ?: "잘못된 요청입니다.")
        } catch (e: Exception) {
            Result.internalServerError("언어 생성 중 오류가 발생했습니다.")
        }
    }

    @GetMapping("/{id}")
    fun getLanguageById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        return try {
            val languageResponseDto = availableLanguageService.getLanguageById(id)
            Result.ok(languageResponseDto)
        } catch (e: IllegalArgumentException) {
            Result.notFound(e.message ?: "해당 언어를 찾을 수 없습니다.")
        } catch (e: Exception) {
            Result.internalServerError("언어 조회 중 오류가 발생했습니다.")
        }
    }

    @GetMapping
    fun getAllLanguages(): ResponseEntity<Map<String, Any>> {
        return try {
            val languages = availableLanguageService.getAllLanguages()
            Result.ok(languages)
        } catch (e: Exception) {
            Result.internalServerError("언어 목록 조회 중 오류가 발생했습니다.")
        }
    }
}
