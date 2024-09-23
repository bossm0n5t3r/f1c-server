package me.f1c.port.chat

import org.springframework.core.io.Resource

interface AiChat {
    fun generateSummaryWithClient(
        template: Resource,
        parameters: Map<String, String>,
    ): GeneratedSummaryDto

    fun generateSummaryWithClient(prompt: String): GeneratedSummaryDto
}
