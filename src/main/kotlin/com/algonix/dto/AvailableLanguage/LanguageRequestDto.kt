package com.algonix.dto.AvailableLanguage

data class LanguageRequestDto(
    val name: String,
    val compileCommand: String,
    val executeCommand: String,
    val version: String,
    val exampleCode: String,
    val executeFileName: String
)
