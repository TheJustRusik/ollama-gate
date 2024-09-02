package org.kenuki.ollamagate.web.controllers

import org.kenuki.ollamagate.core.aspects.Secured
import org.kenuki.ollamagate.core.services.LlamaService
import org.kenuki.ollamagate.web.dtos.requests.GenerateDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LlamaController (
    val llamaService: LlamaService
){
    @Secured
    @PostMapping("/generate")
    fun generateResponse(@RequestBody requestBody: GenerateDTO) = llamaService.llamaGenerate(requestBody)

}