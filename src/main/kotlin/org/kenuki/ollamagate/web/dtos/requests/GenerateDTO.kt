package org.kenuki.ollamagate.web.dtos.requests

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Generates llama response for \"prompt\" wouldn't save chat history. At least should have \"prompt\" property")
data class GenerateDTO (
    @Schema(description = "Your question for llama", example = "Generate python code:\ndef sum(a, b):")
    val prompt: String,
    @Schema(
        description = "This is end of llama response, if given, llama will generate response for that end",
        example = "return sum"
    )
    val suffix: String?,
    @Schema(description = "Conversation pattern, (how should llama response)", example = "Me:Hi\nYou:Hi!\nMe:what is 1 + 2?\nYou:3")
    val template: String?,
    @Schema(description = "The llama's response will generate context integers, use them in the next request to make the llama \"remember\" the previous response.")
    val context: String?,
    @Schema(description = "ADVANCED: Additional options for llm")
    val options: HashMap<String, String>?
)