package org.kenuki.ollamagate.core.services

import org.kenuki.ollamagate.core.configs.util.CustomUserDetails
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService (
    val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByUsername(username) ?: throw UsernameNotFoundException("User $username not found")
        return CustomUserDetails(user)
    }
}