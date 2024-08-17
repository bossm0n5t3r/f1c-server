package me.f1c.domain.pit

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Pits : BaseLongIdTable("pits") {
    val sessionKey = integer("session_key")
    val meetingKey = integer("meeting_key")
    val date = datetime("date")
    val driverNumber = integer("driver_number")
    val pitDuration = decimal("pit_duration", 6, 3)
    val lapNumber = integer("lap_number")
}
