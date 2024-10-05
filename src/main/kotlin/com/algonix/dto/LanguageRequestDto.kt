package com.algonix.dto

data class LanguageRequestDto(
    val name: String,
    val compileCommand: String,
    val executeCommand: String,
    val version: String,
    val exampleCode: String
)
