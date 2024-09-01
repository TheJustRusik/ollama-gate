package org.kenuki.ollamagate.core.services

import jakarta.transaction.Transactional
import org.kenuki.ollamagate.core.entities.Token
import org.kenuki.ollamagate.core.repositories.TokenRepository
import org.kenuki.ollamagate.core.repositories.UserRepository
import org.kenuki.ollamagate.web.dtos.requests.CreateTokenDTO
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class TokenService (
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createToken(createTokenDTO: CreateTokenDTO) : String {
        val user = userRepository.findUserByUsername(createTokenDTO.username)
        val token = Token(
            createTokenDTO.title,
            createTokenDTO.description,
            generateToken(),
            user!!
        )
        tokenRepository.save(token)
        return "/"
    }
    @Transactional
    fun deleteToken(title: String) : String {
        tokenRepository.deleteById(title)
        return "/"
    }

    fun generateToken() : String {
        val tokenBytes = ByteArray(32)
        SecureRandom().nextBytes(tokenBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes)
    }

    fun verifyToken(token: String) : Boolean = tokenRepository.existsTokenByToken(token)
}
