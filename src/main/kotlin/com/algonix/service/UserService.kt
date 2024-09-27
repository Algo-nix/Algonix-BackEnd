package com.algonix.service

import com.algonix.dto.LoginRequest
import com.algonix.dto.SignupRequest
import com.algonix.model.User
import com.algonix.repository.UserRepository
import com.algonix.security.JwtTokenProvider
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun signup(signupRequest: SignupRequest): User? {
        // 중복된 사용자 이름 또는 이메일 체크
        if (existsByUsername(signupRequest.username) || existsByEmail(signupRequest.email)) {
            return null
        }

        return try {
            // 사용자 생성 및 비밀번호 암호화
            val user = User(
                username = signupRequest.username,
                password = passwordEncoder.encode(signupRequest.password),
                email = signupRequest.email
            )
            userRepository.save(user)  // 사용자 저장
            user
        } catch (e: DataIntegrityViolationException) {
            throw IllegalStateException("중복된 아이디 또는 이메일입니다.")  // 회원가입 중 오류 처리 (Controller에서 처리)
        }
    }

    fun login(loginRequest: LoginRequest): TokenResponse? {
        return try {
            // 사용자 인증
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            // 인증 정보 설정
            SecurityContextHolder.getContext().authentication = authentication
            // JWT 토큰 생성
            val jwt = jwtTokenProvider.generateToken(authentication)
            TokenResponse(jwt)
        } catch (e: Exception) {
            null  // 로그인 실패 시 null 반환
        }
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }
}

data class TokenResponse(
    val token: String
)
