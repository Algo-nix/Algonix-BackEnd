package com.algonix.controller

import com.algonix.dto.LoginRequest
import com.algonix.dto.SignupRequest
import com.algonix.model.User
import com.algonix.security.JwtTokenProvider
import com.algonix.service.UserService
import com.algonix.util.ApiResponse
import com.algonix.util.ResultUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<ApiResponse<Any>> {
        if (userService.existsByUsername(signupRequest.username)) {
            return ResultUtil.error("중복된 아이디입니다.", HttpStatus.CONFLICT)
        }

        if (userService.existsByEmail(signupRequest.email)) {
            return ResultUtil.error("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT)
        }

        return try {
            val user = User(
                username = signupRequest.username,
                password = signupRequest.password,
                email = signupRequest.email
            )
            userService.save(user)
            ResultUtil.success(message = "회원가입 성공")
        } catch (e: DataIntegrityViolationException) {
            ResultUtil.error("회원가입 중 오류가 발생했습니다: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<Any>> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtTokenProvider.generateToken(authentication)
            ResultUtil.success(TokenResponse(jwt), "로그인 성공")
        } catch (e: Exception) {
            ResultUtil.error("로그인 실패: ${e.message}")
        }
    }
}


data class TokenResponse(
    val token: String
)
