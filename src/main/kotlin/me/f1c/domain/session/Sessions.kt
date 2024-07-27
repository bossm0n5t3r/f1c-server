package me.f1c.domain.session

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Sessions : BaseLongIdTable("sessions") {
    val sessionKey = integer("session_key")
    val sessionName = varchar("session_name", 50)
    val dateStart = datetime("date_start")
    val dateEnd = datetime("date_end")
    val gmtOffset = varchar("gmt_offset", 10)
    val sessionType = varchar("session_type", 50)
    val meetingKey = integer("meeting_key")
    val location = varchar("location", 50)
    val countryCode = varchar("country_code", 3)
    val countryKey = integer("country_key")
    val countryName = varchar("country_name", 50)
    val circuitKey = integer("circuit_key")
    val circuitShortName = varchar("circuit_short_name", 50)
    val year = integer("year")
}
