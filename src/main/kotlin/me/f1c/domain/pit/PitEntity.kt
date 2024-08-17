package me.f1c.domain.pit

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PitEntity(id: EntityID<Long>) : BaseLongEntity(id, Pits) {
    companion object : BaseLongEntityClass<PitEntity>(Pits)

    var sessionKey by Pits.sessionKey
    var meetingKey by Pits.meetingKey
    var date by Pits.date
    var driverNumber by Pits.driverNumber
    var pitDuration by Pits.pitDuration
    var lapNumber by Pits.lapNumber
}
