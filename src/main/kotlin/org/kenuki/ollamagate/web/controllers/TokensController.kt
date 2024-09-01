package org.kenuki.ollamagate.web.controllers

import org.kenuki.ollamagate.core.entities.Token
import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.kenuki.ollamagate.core.services.TokenService
import org.kenuki.ollamagate.web.dtos.requests.CreateTokenDTO
import org.springframework.context.annotation.Description
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.SecureRandom

@Controller
class TokensController (
    private val tokenService: TokenService
){
    @PostMapping("/token/delete")
    fun deleteToken(@RequestParam title: String): String {
        val page = tokenService.deleteToken(title)
        return "redirect:$page"
    }
    @PostMapping("/token")
    fun createToken(@ModelAttribute createTokenDTO: CreateTokenDTO): String {
        val page = tokenService.createToken(createTokenDTO)
        return "redirect:$page"
    }
}