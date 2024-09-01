package org.kenuki.ollamagate.core.services

import jakarta.transaction.Transactional
import org.kenuki.ollamagate.core.entities.Roles
import org.kenuki.ollamagate.core.entities.User
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.kenuki.ollamagate.web.dtos.requests.ChangePasswordDTO
import org.kenuki.ollamagate.web.dtos.requests.CreateUserDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Service
class UsersService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun createUser(@ModelAttribute createUserDTO: CreateUserDTO): String {
        if (createUserDTO.password != createUserDTO.password2)
            throw Exception("Password mismatch")
        val user = User(
            createUserDTO.username,
            passwordEncoder.encode(createUserDTO.password),
            when (createUserDTO.role) {
                "ROLE_ADMIN" -> Roles.ADMIN
                else -> Roles.USER
            },
            mutableSetOf()
        )
        userRepository.save(user)
        return "/users"
    }
    @Transactional
    fun deleteUser(@RequestParam username: String): String {
        val currentUserName = SecurityContextHolder.getContext().authentication.name
        if (currentUserName == username)
            throw Exception("Can't delete yourself")
        userRepository.deleteByUsername(username)
        return "/users"
    }
    @Transactional
    fun changePassword(changePasswordDTO: ChangePasswordDTO): String {
        if (changePasswordDTO.password != changePasswordDTO.password2)
            throw Exception("Password mismatch")
        val currentUserName = SecurityContextHolder.getContext().authentication.name
        val password = passwordEncoder.encode(changePasswordDTO.password)
        val user = userRepository.findUserByUsername(currentUserName) ?: throw Exception("User not found")
        user.password = password
        userRepository.save(user)
        return "about"

    }
}