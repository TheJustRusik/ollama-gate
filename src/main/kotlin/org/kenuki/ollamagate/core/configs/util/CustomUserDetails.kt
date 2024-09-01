package org.kenuki.ollamagate.core.configs.util

import org.kenuki.ollamagate.core.entities.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails (
    private val user: User
) : UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username


}
