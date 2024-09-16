package me.f1c.adapter.lap

import me.f1c.domain.lap.LapDto
import me.f1c.domain.lap.LapEntity
import me.f1c.domain.lap.Laps
import me.f1c.domain.lap.toDto
import me.f1c.port.lap.LapRepository
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class LapRepositoryImpl(
    private val database: Database,
) : LapRepository {
    override fun batchInsert(lapDtoList: List<LapDto>): Int =
        transaction(database) {
            Laps.batchInsert(lapDtoList) {
                this[Laps.meetingKey] = it.meetingKey
                this[Laps.sessionKey] = it.sessionKey
                this[Laps.driverNumber] = it.driverNumber
                this[Laps.i1Speed] = it.i1Speed
                this[Laps.i2Speed] = it.i2Speed
                this[Laps.stSpeed] = it.stSpeed
                this[Laps.dateStart] = it.dateStart?.toKotlinLocalDateTime()
                this[Laps.lapDuration] = it.lapDuration
                this[Laps.isPitOutLap] = it.isPitOutLap
                this[Laps.durationSector1] = it.durationSector1
                this[Laps.durationSector2] = it.durationSector2
                this[Laps.durationSector3] = it.durationSector3
                this[Laps.segmentsSector1List] = it.segmentsSector1.joinToString()
                this[Laps.segmentsSector2List] = it.segmentsSector2.joinToString()
                this[Laps.segmentsSector3List] = it.segmentsSector3.joinToString()
                this[Laps.lapNumber] = it.lapNumber
            }
        }.size

    override fun findAllBySessionKey(sessionKey: Int): List<LapDto> =
        transaction(database) {
            Laps
                .selectAll()
                .where { Laps.sessionKey eq sessionKey }
                .run { LapEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
