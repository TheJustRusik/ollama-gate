package org.kenuki.ollamagate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OllamaGateApplication

fun main(args: Array<String>) {
    runApplication<OllamaGateApplication>(*args)
}
