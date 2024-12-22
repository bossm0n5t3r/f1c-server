package me.f1c.domain.token

import me.f1c.port.token.TokenClientRepository
import org.springframework.stereotype.Service

@Service
class TokenClientService(
    private val tokenClientRepository: TokenClientRepository,
) {
    fun findByClientId(clientId: String): TokenClientDto? = tokenClientRepository.findByClientId(clientId)

    fun save(tokenClientDto: TokenClientDto): TokenClientDto = tokenClientRepository.save(tokenClientDto)

    fun update(tokenClientDto: TokenClientDto): TokenClientDto? = tokenClientRepository.update(tokenClientDto)
}
