package com.algonix.service

import com.algonix.repository.UserRepository
import com.algonix.security.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("해당 사용자 이름을 가진 사용자를 찾을 수 없습니다: $username") }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        return UserDetailsImpl(
            id = user.id,
            username = user.username,
            password = user.password,
            authorities = authorities
        )
    }
}
