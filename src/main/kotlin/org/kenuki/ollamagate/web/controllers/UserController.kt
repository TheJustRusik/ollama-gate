package org.kenuki.ollamagate.web.controllers

import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController (
    val userRepository: UserRepository,
    val tokenRepository: TokenRepository
){
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
    @GetMapping("/")
    fun welcome(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val role = authentication.authorities.first().authority
        val username = authentication.name
        val isAdmin = role == "ROLE_ADMIN"
        model.addAttribute(
            "token_head",
            "Hello $username! ${if (isAdmin) "Here is all tokens:" else "Here is all your tokens:"}"
        )
        model.addAttribute(
            "tokens",
            if (isAdmin)
                tokenRepository.findAll()
            else
                tokenRepository.findAllByOwner(userRepository.findUserByUsername(username)!!))

        return "main"
    }
}