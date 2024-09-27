package com.algonix.controller

import com.algonix.dto.LoginRequest
import com.algonix.dto.SignupRequest
import com.algonix.service.UserService
import com.algonix.util.Result
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<Map<String, Any>> {
        return try {
            userService.signup(signupRequest)
            Result.created("회원가입 성공")
        } catch (e: IllegalArgumentException) {
            Result.conflict("중복된 아이디 또는 이메일입니다.")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Map<String, Any>> {
        val tokenResponse = userService.login(loginRequest)
        return if (tokenResponse == null) {
            Result.unauthorized("로그인 실패: 잘못된 아이디 또는 비밀번호")
        } else {
            Result.ok(tokenResponse)
        }
    }
}
