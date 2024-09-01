package org.kenuki.ollamagate.core.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.kenuki.ollamagate.core.dtos.requests.LLamaGenerateRequest
import org.kenuki.ollamagate.core.dtos.responses.LlamaGenerateResponse
import org.kenuki.ollamagate.web.dtos.requests.GenerateDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LlamaService (
    val restTemplate: RestTemplate,
    val objectMapper: ObjectMapper,
    @Value("\${llama.model}")
    val llamaModel: String,
    @Value("\${llama.url}")
    val llamaUrl: String
){
    fun llamaGenerate(generateDTO: GenerateDTO): LlamaGenerateResponse {
        val httpEntity = HttpEntity(
            LLamaGenerateRequest(
                llamaModel,
                generateDTO.prompt,
                false,
                generateDTO.suffix,
                generateDTO.options
            )
        )
        return restTemplate.postForEntity("$llamaUrl/api/generate", httpEntity, LlamaGenerateResponse::class.java).body ?: throw Exception("No response")
    }
}