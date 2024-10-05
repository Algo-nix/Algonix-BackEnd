package com.algonix.service

import com.algonix.dto.AvailableLanguage.LanguageRequestDto
import com.algonix.dto.AvailableLanguage.LanguageResponseDto
import com.algonix.model.AvailableLanguage
import com.algonix.repository.AvailableLanguageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AvailableLanguageService(
    private val availableLanguageRepository: AvailableLanguageRepository
) {

    @Transactional
    fun createLanguage(languageRequestDto: LanguageRequestDto): LanguageResponseDto {
        // 언어 데이터 생성
        val availableLanguage = AvailableLanguage(
            name = languageRequestDto.name,
            compileCommand = languageRequestDto.compileCommand,
            executeCommand = languageRequestDto.executeCommand,
            version = languageRequestDto.version,
            exampleCode = languageRequestDto.exampleCode
        )

        // 언어 저장
        val savedLanguage = availableLanguageRepository.save(availableLanguage)
        return toLanguageResponseDto(savedLanguage)
    }

    fun getLanguageById(id: Long): LanguageResponseDto {
        val language = availableLanguageRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 언어를 찾을 수 없습니다.") }
        return toLanguageResponseDto(language)
    }

    fun getAllLanguages(): List<LanguageResponseDto> {
        val languages = availableLanguageRepository.findAll()
        return languages.map { language -> toLanguageResponseDto(language) }
    }

    // AvailableLanguage를 LanguageResponseDto로 변환하는 메서드
    private fun toLanguageResponseDto(language: AvailableLanguage): LanguageResponseDto {
        return LanguageResponseDto(
            id = language.id,
            name = language.name,
            compileCommand = language.compileCommand,
            executeCommand = language.executeCommand,
            version = language.version,
            exampleCode = language.exampleCode
        )
    }
}
