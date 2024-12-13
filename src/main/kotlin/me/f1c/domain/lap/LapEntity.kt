package me.f1c.domain.lap

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LapEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Laps) {
    companion object : BaseLongEntityClass<LapEntity>(Laps)

    var season by Laps.season
    var round by Laps.round
    var raceName by Laps.raceName
    var circuitId by Laps.circuitId
    var raceDatetime by Laps.raceDatetime
    var lapNumber by Laps.lapNumber
    var positions by Laps.positions
}
