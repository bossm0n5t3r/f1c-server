package me.f1c.adapter.schedule

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import me.f1c.domain.schedule.RaceScheduleDto
import me.f1c.domain.schedule.RaceScheduleEntity
import me.f1c.domain.schedule.RaceSchedules
import me.f1c.domain.schedule.toDto
import me.f1c.port.schedule.RaceScheduleRepository
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class RaceScheduleRepositoryImpl(
    private val database: Database,
) : RaceScheduleRepository {
    override fun findAll(): List<RaceScheduleDto> =
        transaction(database) {
            RaceSchedules
                .selectAll()
                .run { RaceScheduleEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    override fun batchInsert(raceScheduleDtoList: List<RaceScheduleDto>): Int =
        transaction(database) {
            RaceSchedules
                .batchInsert(raceScheduleDtoList) {
                    this[RaceSchedules.season] = it.season
                    this[RaceSchedules.round] = it.round
                    this[RaceSchedules.url] = it.url
                    this[RaceSchedules.raceName] = it.raceName
                    this[RaceSchedules.circuitId] = it.circuitId
                    this[RaceSchedules.circuitName] = it.circuitName
                    this[RaceSchedules.raceType] = it.raceType
                    this[RaceSchedules.raceDatetime] = it.raceDatetime.toKotlinLocalDateTime()
                }.size
        }

    override fun findAllByRaceType(raceType: String): List<RaceScheduleDto> =
        transaction(database) {
            RaceSchedules
                .selectAll()
                .where { RaceSchedules.raceType eq raceType }
                .run { RaceScheduleEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    override fun findAllByYearAndMonth(
        year: Int,
        month: Int,
    ): List<RaceScheduleDto> =
        transaction(database) {
            RaceSchedules
                .selectAll()
                .where {
                    val (from, to) = getMonthStartAndEndDates(year, month)
                    RaceSchedules.raceDatetime.between(from, to)
                }.run { RaceScheduleEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    private fun getMonthStartAndEndDates(
        year: Int,
        month: Int,
    ): Pair<LocalDateTime, LocalDateTime> {
        val firstDayOfMonth = LocalDate(year, month, 1)
        val lastDayOfMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

        return firstDayOfMonth.atTime(0, 0) to lastDayOfMonth.atTime(23, 59, 59, 999_999_999)
    }
}
