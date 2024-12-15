package me.f1c.adapter.chat

import me.f1c.domain.chat.AiRaceResultSummaries
import me.f1c.domain.chat.AiRaceResultSummaryDto
import me.f1c.domain.chat.AiRaceResultSummaryEntity
import me.f1c.domain.chat.toDto
import me.f1c.port.chat.AiRaceResultSummaryRepository
import me.f1c.port.chat.CreateAiRaceResultSummaryDto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.stereotype.Repository

@Repository
class AiRaceResultSummaryRepositoryImpl(
    private val database: Database,
) : AiRaceResultSummaryRepository {
    override fun save(createAiRaceResultSummaryDto: CreateAiRaceResultSummaryDto): Long =
        transaction(database) {
            AiRaceResultSummaries.insertAndGetId {
                it[season] = createAiRaceResultSummaryDto.season
                it[round] = createAiRaceResultSummaryDto.round
                it[prompt] = createAiRaceResultSummaryDto.prompt
                it[summary] = createAiRaceResultSummaryDto.summary
                it[revision] = createAiRaceResultSummaryDto.revision
                it[chatModel] = createAiRaceResultSummaryDto.chatModel.name
            }
        }.value

    override fun findLatestBySeasonAndRoundAndChatModel(
        season: Int,
        round: Int,
        chatModel: OpenAiApi.ChatModel,
    ): AiRaceResultSummaryDto? =
        transaction(database) {
            AiRaceResultSummaries
                .selectAll()
                .where { AiRaceResultSummaries.season eq season }
                .andWhere { AiRaceResultSummaries.round eq round }
                .andWhere { AiRaceResultSummaries.chatModel eq chatModel.name }
                .orderBy(AiRaceResultSummaries.revision, SortOrder.DESC)
                .firstOrNull()
                ?.let { AiRaceResultSummaryEntity.wrapRow(it) }
                ?.toDto()
        }

    override fun findById(id: Long): AiRaceResultSummaryDto? =
        transaction(database) {
            AiRaceResultSummaryEntity.findById(id)?.toDto()
        }
}
