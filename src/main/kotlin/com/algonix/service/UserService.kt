package com.algonix.service

import com.algonix.dto.Auth.LoginRequestDto
import com.algonix.dto.Auth.SignupRequestDto
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

    fun signup(signupRequestDto: SignupRequestDto): User {
        // 중복된 사용자 이름 또는 이메일 체크
        if (existsByUsername(signupRequestDto.username) || existsByEmail(signupRequestDto.email)) {
            throw IllegalArgumentException("중복된 아이디 또는 이메일입니다.")
        }

        return try {
            // 사용자 생성 및 비밀번호 암호화
            val user = User(
                username = signupRequestDto.username,
                password = passwordEncoder.encode(signupRequestDto.password),
                email = signupRequestDto.email,
                nickname = signupRequestDto.nickname,
                organization = signupRequestDto.organization,
                visibility = signupRequestDto.visibility,
                statusMessage = signupRequestDto.statusMessage
            )
            userRepository.save(user)  // 사용자 저장
            user
        } catch (e: DataIntegrityViolationException) {
            throw IllegalStateException("회원가입 처리 중 오류가 발생했습니다.")
        }
    }


    fun login(loginRequestDto: LoginRequestDto): TokenResponse? {
        return try {
            // 사용자 인증
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequestDto.username, loginRequestDto.password)
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
