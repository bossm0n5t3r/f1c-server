package me.f1c.domain.lap

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Laps : BaseLongIdTable("laps") {
    val meetingKey = integer("meeting_key")
    val sessionKey = integer("session_key")
    val driverNumber = integer("driver_number")

    val i1Speed = integer("i1_speed").nullable()
    val i2Speed = integer("i2_speed").nullable()
    val stSpeed = integer("st_speed").nullable()

    val dateStart = datetime("date_start").nullable()
    val lapDuration = decimal("lap_duration", 6, 3).nullable()

    val isPitOutLap = bool("is_pit_out_lap")

    val durationSector1 = decimal("duration_sector_1", 6, 3).nullable()
    val durationSector2 = decimal("duration_sector_2", 6, 3).nullable()
    val durationSector3 = decimal("duration_sector_3", 6, 3).nullable()

    val segmentsSector1List = varchar("segments_sector_1_list", 100)
    val segmentsSector2List = varchar("segments_sector_2_list", 100)
    val segmentsSector3List = varchar("segments_sector_3_list", 100)

    val lapNumber = integer("lap_number")
}
