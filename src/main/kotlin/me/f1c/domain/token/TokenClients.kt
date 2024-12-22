package me.f1c.domain.token

import me.f1c.domain.BaseLongIdTable
import me.f1c.security.TokenRole

object TokenClients : BaseLongIdTable("token_clients") {
    val clientId = varchar("client_id", 255).uniqueIndex()
    val clientSecret = varchar("client_secret", 255)
    val name = varchar("name", 255)
    val role = enumerationByName<TokenRole>("role", 10)
    val status = enumerationByName<TokenClientStatus>("status", 10)
}
