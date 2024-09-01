package org.kenuki.ollamagate.core.repositories

import org.kenuki.ollamagate.core.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findUserByUsername(username: String): User?
}