package me.f1c.domain.session

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Sessions : BaseLongIdTable("sessions") {
    val sessionKey = integer("session_key")
    val sessionName = varchar("session_name", 50).nullable()
    val dateStart = datetime("date_start").nullable()
    val dateEnd = datetime("date_end").nullable()
    val gmtOffset = varchar("gmt_offset", 10).nullable()
    val sessionType = varchar("session_type", 50).nullable()
    val meetingKey = integer("meeting_key")
    val location = varchar("location", 50)
    val countryCode = varchar("country_code", 3).nullable()
    val countryKey = integer("country_key")
    val countryName = varchar("country_name", 50).nullable()
    val circuitKey = integer("circuit_key")
    val circuitShortName = varchar("circuit_short_name", 50).nullable()
    val year = integer("year")
}
