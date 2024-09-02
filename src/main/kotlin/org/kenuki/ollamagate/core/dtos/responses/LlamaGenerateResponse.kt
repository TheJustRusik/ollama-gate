package org.kenuki.ollamagate.core.dtos.responses

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response from llama llm")
data class LlamaGenerateResponse (
    val response: String
)