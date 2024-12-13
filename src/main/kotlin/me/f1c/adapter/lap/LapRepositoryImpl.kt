package me.f1c.adapter.lap

import kotlinx.datetime.LocalDateTime
import me.f1c.domain.lap.LapDto
import me.f1c.domain.lap.LapEntity
import me.f1c.domain.lap.Laps
import me.f1c.domain.lap.toDto
import me.f1c.port.lap.LapRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.andWhere
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
                this[Laps.season] = it.season
                this[Laps.round] = it.round
                this[Laps.raceName] = it.raceName
                this[Laps.circuitId] = it.circuitId
                this[Laps.raceDatetime] = LocalDateTime.parse(it.raceDatetime)
                this[Laps.lapNumber] = it.lapNumber
                this[Laps.positions] = it.positions
            }
        }.size

    override fun findAllBySeasonAndRound(
        season: Int,
        round: Int,
    ): List<LapDto> =
        transaction(database) {
            Laps
                .selectAll()
                .where { Laps.season eq season }
                .andWhere { Laps.round eq round }
                .run { LapEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
