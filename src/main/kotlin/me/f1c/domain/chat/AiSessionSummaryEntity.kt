package me.f1c.domain.chat

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AiSessionSummaryEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, AiSessionSummaries) {
    companion object : BaseLongEntityClass<AiSessionSummaryEntity>(AiSessionSummaries)

    var sessionKey by AiSessionSummaries.sessionKey
    var prompt by AiSessionSummaries.prompt
    var summary by AiSessionSummaries.summary
    var revision by AiSessionSummaries.revision
}
