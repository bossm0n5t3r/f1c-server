package me.f1c.adapter.chat

import me.f1c.port.chat.AiChat
import org.springframework.ai.chat.client.ChatClient
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class OpenAiChat(
    private val chatClient: ChatClient,
) : AiChat {
    override fun chatWithClient(
        template: Resource,
        parameters: Map<String, String>,
    ): String =
        chatClient
            .prompt()
            .user { userSpec ->
                val request = userSpec.text(template)
                for ((key, value) in parameters) {
                    request.param(key, value)
                }
            }.call()
            .content()
}
