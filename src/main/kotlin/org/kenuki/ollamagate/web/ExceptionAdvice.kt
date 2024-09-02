package org.kenuki.ollamagate.web

import org.kenuki.ollamagate.core.exceptions.AuthorizationException
import org.kenuki.ollamagate.web.dtos.responses.ExceptionResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponseDTO> {
        val responseDTO = ExceptionResponseDTO("Error! Reason: ${e.message}")
        return ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR)
    }
    @ExceptionHandler(AuthorizationException::class)
    fun handleAuthorizationException(e: Exception): ResponseEntity<ExceptionResponseDTO> {
        val responseDTO = ExceptionResponseDTO("Not authorized")
        return ResponseEntity(responseDTO, HttpStatus.UNAUTHORIZED)
    }
}