package org.kenuki.ollamagate.core.repositories

import org.kenuki.ollamagate.core.entities.Token
import org.kenuki.ollamagate.core.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, String> {
    fun findAllByOwner(owner: User): List<Token>
    fun existsTokenByToken(token: String): Boolean
    fun getTokenByToken(token: String): Token?
}