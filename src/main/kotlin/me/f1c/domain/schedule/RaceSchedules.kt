package me.f1c.domain.schedule

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RaceSchedules : BaseLongIdTable("race_schedules") {
    val season = integer("season")
    val round = integer("round")
    val url = varchar("url", 500)
    val raceName = varchar("race_name", 200)
    val circuitId = varchar("circuit_id", 100)
    val circuitName = varchar("circuit_name", 200)
    val raceType = varchar("race_type", 100)
    val raceDatetime = datetime("race_datetime")
}
