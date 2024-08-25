package me.f1c.adapter.position

import me.f1c.domain.position.PositionDto
import me.f1c.domain.position.PositionEntity
import me.f1c.domain.position.Positions
import me.f1c.domain.position.toDto
import me.f1c.port.position.PositionRepository
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class PositionRepositoryImpl(
    private val database: Database,
) : PositionRepository {
    override fun batchInsert(positionDtoList: List<PositionDto>): Int =
        transaction(database) {
            Positions.batchInsert(positionDtoList) {
                this[Positions.date] = it.date.toKotlinLocalDateTime()
                this[Positions.meetingKey] = it.meetingKey
                this[Positions.sessionKey] = it.sessionKey
                this[Positions.driverNumber] = it.driverNumber
                this[Positions.position] = it.position
            }
        }.size

    override fun findAllBySessionKey(sessionKey: Int): List<PositionDto> =
        transaction(database) {
            Positions.selectAll().where { Positions.sessionKey eq sessionKey }.run { PositionEntity.wrapRows(this) }.map { it.toDto() }
        }
}
