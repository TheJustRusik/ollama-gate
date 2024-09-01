package org.kenuki.ollamagate.web.dtos.requests

data class CreateTokenDTO(
    val username: String,
    val title: String,
    val description: String,
)
