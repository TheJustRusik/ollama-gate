package org.kenuki.ollamagate.web.dtos.requests

data class ChangePasswordDTO(
    val password: String,
    val password2: String
)
