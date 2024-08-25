package me.f1c.domain.position

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Positions : BaseLongIdTable("positions") {
    val date = datetime("date")
    val meetingKey = integer("meeting_key")
    val sessionKey = integer("session_key")
    val driverNumber = integer("driver_number")
    val position = integer("position")
}
