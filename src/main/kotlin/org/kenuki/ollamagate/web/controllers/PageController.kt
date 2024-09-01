package org.kenuki.ollamagate.web.controllers

import org.kenuki.ollamagate.core.aspects.AddHeadHelloToModel
import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.kenuki.ollamagate.core.services.UsersService
import org.kenuki.ollamagate.web.dtos.requests.ChangePasswordDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class PageController (
    val userRepository: UserRepository,
    val tokenRepository: TokenRepository,
    val usersService: UsersService
){
    @AddHeadHelloToModel
    @GetMapping("/about")
    fun pageAbout(model: Model): String {
        return "about"
    }
    @GetMapping("/login")
    fun pageLogin(): String {
        return "login"
    }
    @AddHeadHelloToModel
    @GetMapping("/")
    fun pageHome(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val role = authentication.authorities.first().authority
        val username = authentication.name
        val user = userRepository.findUserByUsername(username)
        val isAdmin = role == "ROLE_ADMIN"

        model.addAttribute(
            "tokens",
            if (isAdmin) tokenRepository.findAll() else tokenRepository.findAllByOwner(user!!)
        )
        model.addAttribute("all_tokens", if (isAdmin) "All tokens:" else "Your tokens:")
        model.addAttribute("admin", isAdmin)

        val users = userRepository.findAll()

        model.addAttribute("users", users)
        return "main"
    }
    @AddHeadHelloToModel
    @GetMapping("/users")
    fun pageUsers(model: Model): String {
        model.addAttribute("users", userRepository.findAll())
        return "users"
    }
    @PostMapping("/password")
    fun changePassword(@ModelAttribute changePasswordDTO: ChangePasswordDTO): String = "redirect:" + usersService.changePassword(changePasswordDTO)

}