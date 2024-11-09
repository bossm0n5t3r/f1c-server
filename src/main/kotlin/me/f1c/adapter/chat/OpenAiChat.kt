package me.f1c.adapter.chat

import me.f1c.port.chat.AiChat
import me.f1c.port.chat.GeneratedSummaryDto
import org.springframework.ai.chat.client.ChatClient
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class OpenAiChat(
    private val chatClient: ChatClient,
) : AiChat {
    override fun generateSummaryWithClient(
        template: Resource,
        parameters: Map<String, String>,
    ): GeneratedSummaryDto =
        chatClient
            .prompt()
            .user { userSpec ->
                val request = userSpec.text(template)
                for ((key, value) in parameters) {
                    request.param(key, value)
                }
            }.call()
            .entity(GeneratedSummaryDto::class.java)
            ?: GeneratedSummaryDto()

    override fun generateSummaryWithClient(prompt: String): GeneratedSummaryDto =
        chatClient
            .prompt()
            .user(prompt)
            .call()
            .entity(GeneratedSummaryDto::class.java)
            ?: GeneratedSummaryDto()
}
