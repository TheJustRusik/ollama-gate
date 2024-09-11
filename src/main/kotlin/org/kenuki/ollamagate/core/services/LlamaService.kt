package org.kenuki.ollamagate.core.services

import org.kenuki.ollamagate.core.dtos.requests.LLamaGenerateRequest
import org.kenuki.ollamagate.core.dtos.responses.LlamaGenerateResponse
import org.kenuki.ollamagate.web.dtos.requests.GenerateDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LlamaService(
    val restTemplate: RestTemplate,
    @Value("\${llama.model}")
    val llamaModel: String,
    @Value("\${llama.url}")
    val llamaUrl: String
){
    fun llamaGenerate(generateDTO: GenerateDTO): ResponseEntity<LlamaGenerateResponse> {
        val context = SecurityContextHolder.getContext()
        println(context.authentication)//TODO

        val httpEntity = HttpEntity(
            LLamaGenerateRequest(
                llamaModel,
                generateDTO.prompt,
                false,
                generateDTO.suffix,
                generateDTO.options
            )
        )
        val body = restTemplate.postForEntity("$llamaUrl/api/generate", httpEntity, LlamaGenerateResponse::class.java).body ?: throw Exception("No response")
        return ResponseEntity.ok(body)
    }
}