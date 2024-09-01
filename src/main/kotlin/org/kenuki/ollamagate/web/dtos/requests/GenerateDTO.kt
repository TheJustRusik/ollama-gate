package org.kenuki.ollamagate.web.dtos.requests

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

data class GenerateDTO (
    val prompt: String,
    val suffix: String?,
    val template: String?,
    val context: String?,
    val options: HashMap<String, String>?
)