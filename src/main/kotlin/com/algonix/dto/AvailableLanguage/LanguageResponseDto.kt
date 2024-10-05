package com.algonix.dto.AvailableLanguage

data class LanguageResponseDto(
    val id: Long,
    val name: String,
    val compileCommand: String,
    val executeCommand: String,
    val version: String,
    val exampleCode: String
)
