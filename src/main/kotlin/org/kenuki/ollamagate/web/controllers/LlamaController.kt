package org.kenuki.ollamagate.web.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.kenuki.ollamagate.core.aspects.TokenSecured
import org.kenuki.ollamagate.core.services.LlamaService
import org.kenuki.ollamagate.web.dtos.requests.GenerateDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "LlamaController", description = "Llama API controller")
class LlamaController (
    val llamaService: LlamaService
){
    @TokenSecured
    @PostMapping("/generate")
    @Operation(summary = "Generates a llama response", description = "Provide GenerateDTO to receive llama response, see GenerateDTO to make request")
    fun generateResponse(@RequestBody requestBody: GenerateDTO) = llamaService.llamaGenerate(requestBody)

}