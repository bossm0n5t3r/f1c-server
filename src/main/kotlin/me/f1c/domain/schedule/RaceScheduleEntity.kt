package me.f1c.domain.schedule

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RaceScheduleEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, RaceSchedules) {
    companion object : BaseLongEntityClass<RaceScheduleEntity>(RaceSchedules)

    var season by RaceSchedules.season
    var round by RaceSchedules.round
    var url by RaceSchedules.url
    var raceName by RaceSchedules.raceName
    var circuitId by RaceSchedules.circuitId
    var circuitName by RaceSchedules.circuitName
    var raceType by RaceSchedules.raceType
    var raceDatetime by RaceSchedules.raceDatetime
}
