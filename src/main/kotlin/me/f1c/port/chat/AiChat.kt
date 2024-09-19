package me.f1c.port.chat

import org.springframework.core.io.Resource

interface AiChat {
    fun chatWithClient(
        template: Resource,
        parameters: Map<String, String>,
    ): String
}
