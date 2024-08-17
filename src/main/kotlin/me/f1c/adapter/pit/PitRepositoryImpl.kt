package me.f1c.adapter.pit

import me.f1c.domain.pit.PitDto
import me.f1c.domain.pit.PitEntity
import me.f1c.domain.pit.Pits
import me.f1c.domain.pit.toDto
import me.f1c.port.pit.PitRepository
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class PitRepositoryImpl(
    private val database: Database,
) : PitRepository {
    override fun batchInsert(pitDtoList: List<PitDto>): Int =
        transaction(database) {
            Pits.batchInsert(pitDtoList) {
                this[Pits.sessionKey] = it.sessionKey
                this[Pits.meetingKey] = it.meetingKey
                this[Pits.date] = it.date.toKotlinLocalDateTime()
                this[Pits.driverNumber] = it.driverNumber
                this[Pits.pitDuration] = it.pitDuration
                this[Pits.lapNumber] = it.lapNumber
            }
        }.size

    override fun findAllBySessionKey(sessionKey: Int): List<PitDto> =
        transaction(database) {
            Pits.selectAll().where { Pits.sessionKey eq sessionKey }.run { PitEntity.wrapRows(this) }.map { it.toDto() }
        }
}
