package me.f1c.domain.result

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RaceResults : BaseLongIdTable("race_results") {
    val season = integer("season")
    val round = integer("round")
    val url = varchar("url", 500)
    val raceName = varchar("race_name", 200)
    val circuitId = varchar("circuit_id", 100)
    val circuitName = varchar("circuit_name", 200)
    val raceDatetime = datetime("race_datetime")
    val position = integer("position")
    val driverId = varchar("driver_id", 100)
    val constructorId = varchar("constructor_id", 100)
    val status = varchar("status", 50)
    val timeMillis = long("time_millis").nullable()
    val timeText = varchar("time_text", 50).nullable()
    val fastestLapRank = integer("fastest_lap_rank").nullable()
    val fastestLapLap = varchar("fastest_lap_lap", 50).nullable()
    val fastestLapTime = varchar("fastest_lap_time", 50).nullable()
    val fastestLapAverageSpeedUnits = varchar("fastest_lap_average_speed_units", 10).nullable()
    val fastestLapAverageSpeedSpeed = varchar("fastest_lap_average_speed_speed", 50).nullable()
}
