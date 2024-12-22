package me.f1c.domain.token

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TokenClientEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, TokenClients) {
    companion object : BaseLongEntityClass<TokenClientEntity>(TokenClients)

    var clientId by TokenClients.clientId
    var clientSecret by TokenClients.clientSecret
    var name by TokenClients.name
    var role by TokenClients.role
    var status by TokenClients.status
}
