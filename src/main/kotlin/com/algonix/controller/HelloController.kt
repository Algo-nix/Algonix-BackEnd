package com.algonix.controller

import com.algonix.util.ApiResponse
import com.algonix.util.ResultUtil
import org.springframework.security.core.Authentication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/hello")
class HelloController {

    @GetMapping
    fun hello(authentication: Authentication): ResponseEntity<ApiResponse<Any>> {
        val message = "안녕하세요, ${authentication.name}님!"
        return ResultUtil.success(message, "인사말 호출 성공")
    }
}
