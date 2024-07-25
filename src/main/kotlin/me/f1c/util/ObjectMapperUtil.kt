package me.f1c.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object ObjectMapperUtil {
    val objectMapper: ObjectMapper =
        jacksonObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
}
