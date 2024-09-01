package org.kenuki.ollamagate.web.dtos.requests

data class CreateUserDTO(
    val username: String,
    val password: String,
    val password2: String,
    val role: String
)
