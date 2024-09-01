package me.f1c.domain.driver

import me.f1c.domain.BaseLongIdTable

object Drivers : BaseLongIdTable("drivers") {
    val driverNumber = integer("driver_number")
    val sessionKey = integer("session_key")
    val meetingKey = integer("meeting_key")
    val broadcastName = varchar("broadcast_name", 50)
    val countryCode = varchar("country_code", 3).nullable()
    val firstName = varchar("first_name", 50).nullable()
    val lastName = varchar("last_name", 50).nullable()
    val fullName = varchar("full_name", 50)
    val headshotUrl = varchar("headshot_url", 500).nullable()
    val teamColour = varchar("team_colour", 10).nullable()
    val teamName = varchar("team_name", 50).nullable()
    val nameAcronym = varchar("name_acronym", 50)
}
