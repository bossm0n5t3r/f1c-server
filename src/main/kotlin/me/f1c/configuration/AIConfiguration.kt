package me.f1c.configuration

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AIConfiguration {
    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder): ChatClient = chatClientBuilder.build()
}
