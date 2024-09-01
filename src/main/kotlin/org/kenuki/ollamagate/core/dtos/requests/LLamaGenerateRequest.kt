package org.kenuki.ollamagate.core.dtos.requests

data class LLamaGenerateRequest(
    val model: String,
    val prompt: String?,
    val stream: Boolean?,
    val suffix: String?,
    val options: HashMap<String, String>?
)