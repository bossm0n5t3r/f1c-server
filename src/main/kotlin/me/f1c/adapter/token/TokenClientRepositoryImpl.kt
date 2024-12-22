package me.f1c.adapter.token

import me.f1c.domain.token.TokenClientDto
import me.f1c.domain.token.TokenClientEntity
import me.f1c.domain.token.TokenClients
import me.f1c.domain.token.toDto
import me.f1c.port.token.TokenClientRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class TokenClientRepositoryImpl(
    private val database: Database,
) : TokenClientRepository {
    override fun findByClientId(clientId: String): TokenClientDto? =
        transaction(database) {
            TokenClientEntity
                .find { TokenClients.clientId eq clientId }
                .firstOrNull()
                ?.toDto()
        }

    override fun save(tokenClientDto: TokenClientDto): TokenClientDto =
        transaction(database) {
            TokenClientEntity
                .new {
                    clientId = tokenClientDto.clientId
                    clientSecret = tokenClientDto.clientSecret
                    name = tokenClientDto.name
                    role = tokenClientDto.role
                    status = tokenClientDto.status
                }.toDto()
        }

    override fun update(tokenClientDto: TokenClientDto): TokenClientDto? =
        transaction(database) {
            TokenClientEntity
                .find { TokenClients.clientId eq tokenClientDto.clientId }
                .firstOrNull()
                ?.let {
                    it.clientSecret = tokenClientDto.clientSecret
                    it.name = tokenClientDto.name
                    it.role = tokenClientDto.role
                    it.status = tokenClientDto.status
                    it
                }?.toDto()
        }
}
