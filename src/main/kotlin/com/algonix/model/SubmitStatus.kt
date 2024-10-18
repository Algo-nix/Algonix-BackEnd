package com.algonix.model


enum class SubmitStatus {
    SCORING,
    COMPILE_ERROR,
    TIMEOUT_ERROR,
    MEMORY_ERROR,
    CORRECT,
    INCORRECT
}