package me.f1c.adapter.session

import kotlinx.datetime.toKotlinLocalDateTime
import me.f1c.domain.session.SessionDto
import me.f1c.domain.session.SessionEntity
import me.f1c.domain.session.Sessions
import me.f1c.domain.session.toDto
import me.f1c.port.session.SessionRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class SessionRepositoryImpl(
    private val database: Database,
) : SessionRepository {
    override fun findAll(): List<SessionDto> =
        transaction(database) {
            Sessions.selectAll().run { SessionEntity.wrapRows(this) }.map { it.toDto() }
        }

    override fun findBySessionKey(sessionKey: Int): SessionDto? = this.findAll().find { it.sessionKey == sessionKey }

    override fun findAllSessionKeys(): List<Int> = this.findAll().map { it.sessionKey }

    override fun batchInsert(sessionDtoList: List<SessionDto>): Int =
        transaction(database) {
            Sessions
                .batchInsert(sessionDtoList) {
                    this[Sessions.sessionKey] = it.sessionKey
                    this[Sessions.sessionName] = it.sessionName
                    this[Sessions.dateStart] = OffsetDateTime.parse(it.dateStart).toLocalDateTime().toKotlinLocalDateTime()
                    this[Sessions.dateEnd] = OffsetDateTime.parse(it.dateEnd).toLocalDateTime().toKotlinLocalDateTime()
                    this[Sessions.gmtOffset] = it.gmtOffset
                    this[Sessions.sessionType] = it.sessionType
                    this[Sessions.meetingKey] = it.meetingKey
                    this[Sessions.location] = it.location
                    this[Sessions.countryCode] = it.countryCode
                    this[Sessions.countryKey] = it.countryKey
                    this[Sessions.countryName] = it.countryName
                    this[Sessions.circuitKey] = it.circuitKey
                    this[Sessions.circuitShortName] = it.circuitShortName
                    this[Sessions.year] = it.year
                }.size
        }

    override fun findLatestSession(): SessionDto? =
        transaction(database) {
            Sessions
                .selectAll()
                .orderBy(Sessions.dateStart to SortOrder.DESC)
                .run { SessionEntity.wrapRows(this) }
                .firstOrNull()
                ?.toDto()
        }
}
