package me.f1c.domain.chat

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class AiRaceResultSummaryEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, AiRaceResultSummaries) {
    companion object : BaseLongEntityClass<AiRaceResultSummaryEntity>(AiRaceResultSummaries)

    var season by AiRaceResultSummaries.season
    var round by AiRaceResultSummaries.round
    var prompt by AiRaceResultSummaries.prompt
    var summary by AiRaceResultSummaries.summary
    var revision by AiRaceResultSummaries.revision
    var chatModel by AiRaceResultSummaries.chatModel
}
