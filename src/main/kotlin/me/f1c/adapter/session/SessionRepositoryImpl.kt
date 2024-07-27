package me.f1c.adapter.session

import kotlinx.datetime.toKotlinLocalDateTime
import me.f1c.domain.session.SessionDto
import me.f1c.domain.session.SessionEntity
import me.f1c.domain.session.SessionResponseDto
import me.f1c.domain.session.Sessions
import me.f1c.domain.session.toDto
import me.f1c.port.session.SessionRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class SessionRepositoryImpl(
    private val database: Database,
) : SessionRepository {
    override fun findAll(): List<SessionDto> =
        transaction(database) {
            Sessions.selectAll().run { SessionEntity.wrapRows(this) }.map { it.toDto() }
        }

    override fun findBySessionKey(sessionKey: Int): SessionDto? {
        return this.findAll().find { it.sessionKey == sessionKey }
    }

    override fun findAllSessionKeys(): List<Int> {
        return this.findAll().map { it.sessionKey }
    }

    override fun batchInsert(sessionResponseDtoList: List<SessionResponseDto>): Int =
        transaction(database) {
            Sessions.batchInsert(sessionResponseDtoList) {
                this[Sessions.sessionKey] = it.sessionKey
                this[Sessions.sessionName] = it.sessionName
                this[Sessions.dateStart] = it.dateStart?.toLocalDateTime()?.toKotlinLocalDateTime()
                this[Sessions.dateEnd] = it.dateEnd?.toLocalDateTime()?.toKotlinLocalDateTime()
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
}
