package com.algonix.service

import com.algonix.model.User
import com.algonix.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun save(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }
}
