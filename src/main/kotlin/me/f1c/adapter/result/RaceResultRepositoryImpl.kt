package me.f1c.adapter.result

import kotlinx.datetime.LocalDateTime
import me.f1c.domain.result.RaceResultDto
import me.f1c.domain.result.RaceResultEntity
import me.f1c.domain.result.RaceResults
import me.f1c.domain.result.toDto
import me.f1c.port.result.RaceResultRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class RaceResultRepositoryImpl(
    private val database: Database,
) : RaceResultRepository {
    override fun batchInsert(raceResultDtoList: List<RaceResultDto>): Int =
        transaction(database) {
            RaceResults
                .batchInsert(raceResultDtoList) {
                    this[RaceResults.season] = it.season
                    this[RaceResults.round] = it.round
                    this[RaceResults.url] = it.url
                    this[RaceResults.raceName] = it.raceName
                    this[RaceResults.circuitId] = it.circuitId
                    this[RaceResults.circuitName] = it.circuitName
                    this[RaceResults.raceDatetime] = LocalDateTime.parse(it.raceDatetime)
                    this[RaceResults.position] = it.position
                    this[RaceResults.driverId] = it.driverId
                    this[RaceResults.constructorId] = it.constructorId
                    this[RaceResults.status] = it.status
                    this[RaceResults.timeMillis] = it.timeMillis
                    this[RaceResults.timeText] = it.timeText
                    this[RaceResults.fastestLapRank] = it.fastestLapRank
                    this[RaceResults.fastestLapLap] = it.fastestLapLap
                    this[RaceResults.fastestLapTime] = it.fastestLapTime
                    this[RaceResults.fastestLapAverageSpeedUnits] = it.fastestLapAverageSpeedUnits
                    this[RaceResults.fastestLapAverageSpeedSpeed] = it.fastestLapAverageSpeedSpeed
                }
        }.size

    override fun findAll(): List<RaceResultDto> =
        transaction(database) {
            RaceResults
                .selectAll()
                .run { RaceResultEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    override fun findLatest(): RaceResultDto? =
        transaction(database) {
            RaceResults
                .selectAll()
                .orderBy(RaceResults.id, SortOrder.DESC)
                .firstOrNull()
                ?.let { RaceResultEntity.wrapRow(it) }
                ?.toDto()
        }

    override fun findAllBySeasonAndRound(
        season: Int,
        round: Int,
    ): List<RaceResultDto> =
        transaction(database) {
            RaceResults
                .selectAll()
                .where { RaceResults.season eq season }
                .andWhere { RaceResults.round eq round }
                .run { RaceResultEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
