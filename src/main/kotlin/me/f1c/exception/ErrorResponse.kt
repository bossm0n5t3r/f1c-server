package me.f1c.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val errorMessage: String,
    val details: Map<String, Any?>? = null,
)
