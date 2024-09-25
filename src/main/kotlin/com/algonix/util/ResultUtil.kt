package com.algonix.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

object ResultUtil {

    fun success(message: String = "요청이 성공적으로 처리되었습니다."): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity(
            ApiResponse(
                success = true,
                message = message,
                data = null
            ),
            HttpStatus.OK
        )
    }

    fun <T> success(data: T?, message: String = "요청이 성공적으로 처리되었습니다."): ResponseEntity<ApiResponse<T>> {
        return ResponseEntity(
            ApiResponse(
                success = true,
                message = message,
                data = data
            ),
            HttpStatus.OK
        )
    }

    fun error(message: String, status: HttpStatus = HttpStatus.BAD_REQUEST): ResponseEntity<ApiResponse<Any>> {
        return ResponseEntity(
            ApiResponse(
                success = false,
                message = message,
                data = null
            ),
            status
        )
    }
}
