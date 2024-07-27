package me.f1c.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class BeanConfiguration {
    @Bean
    fun restClient() = RestClient.builder().build()
}
