package me.f1c.domain.lap

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LapEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Laps) {
    companion object : BaseLongEntityClass<LapEntity>(Laps)

    var meetingKey by Laps.meetingKey
    var sessionKey by Laps.sessionKey
    var driverNumber by Laps.driverNumber
    var i1Speed by Laps.i1Speed
    var i2Speed by Laps.i2Speed
    var stSpeed by Laps.stSpeed
    var dateStart by Laps.dateStart
    var lapDuration by Laps.lapDuration
    var isPitOutLap by Laps.isPitOutLap
    var durationSector1 by Laps.durationSector1
    var durationSector2 by Laps.durationSector2
    var durationSector3 by Laps.durationSector3
    var segmentsSector1List by Laps.segmentsSector1List
    var segmentsSector2List by Laps.segmentsSector2List
    var segmentsSector3List by Laps.segmentsSector3List
    var lapNumber by Laps.lapNumber
}
