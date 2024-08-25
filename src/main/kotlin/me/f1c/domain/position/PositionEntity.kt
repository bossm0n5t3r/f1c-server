package me.f1c.domain.position

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PositionEntity(id: EntityID<Long>) : BaseLongEntity(id, Positions) {
    companion object : BaseLongEntityClass<PositionEntity>(Positions)

    var date by Positions.date
    var meetingKey by Positions.meetingKey
    var sessionKey by Positions.sessionKey
    var driverNumber by Positions.driverNumber
    var position by Positions.position
}
