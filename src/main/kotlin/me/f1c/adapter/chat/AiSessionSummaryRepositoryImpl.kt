package me.f1c.adapter.chat

import me.f1c.domain.chat.AiSessionSummaries
import me.f1c.domain.chat.AiSessionSummaryDto
import me.f1c.domain.chat.AiSessionSummaryEntity
import me.f1c.domain.chat.toDto
import me.f1c.port.chat.AiSessionSummaryRepository
import me.f1c.port.chat.CreateAiSessionSummaryDto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class AiSessionSummaryRepositoryImpl(
    private val database: Database,
) : AiSessionSummaryRepository {
    override fun save(createAiSessionSummaryDto: CreateAiSessionSummaryDto): Long =
        transaction(database) {
            AiSessionSummaries.insertAndGetId {
                it[sessionKey] = createAiSessionSummaryDto.sessionKey
                it[prompt] = createAiSessionSummaryDto.prompt
                it[summary] = createAiSessionSummaryDto.summary
                it[revision] = createAiSessionSummaryDto.revision
            }
        }.value

    override fun findLatestBySessionKey(sessionKey: Int): AiSessionSummaryDto? =
        transaction(database) {
            AiSessionSummaries
                .selectAll()
                .where { AiSessionSummaries.sessionKey eq sessionKey }
                .orderBy(AiSessionSummaries.revision, SortOrder.DESC)
                .firstOrNull()
                ?.let { AiSessionSummaryEntity.wrapRow(it) }
                ?.toDto()
        }

    override fun findById(id: Long): AiSessionSummaryDto? =
        transaction(database) {
            AiSessionSummaryEntity.findById(id)?.toDto()
        }
}