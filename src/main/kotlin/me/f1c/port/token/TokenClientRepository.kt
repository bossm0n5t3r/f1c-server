package me.f1c.port.token

import me.f1c.domain.token.TokenClientDto

interface TokenClientRepository {
    fun findByClientId(clientId: String): TokenClientDto?

    fun save(tokenClientDto: TokenClientDto): TokenClientDto

    fun update(tokenClientDto: TokenClientDto): TokenClientDto?
}
