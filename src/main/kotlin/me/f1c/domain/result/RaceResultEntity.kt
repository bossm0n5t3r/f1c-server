package me.f1c.domain.result

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RaceResultEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, RaceResults) {
    companion object : BaseLongEntityClass<RaceResultEntity>(RaceResults)

    var season by RaceResults.season
    var round by RaceResults.round
    var url by RaceResults.url
    var raceName by RaceResults.raceName
    var circuitId by RaceResults.circuitId
    var circuitName by RaceResults.circuitName
    var raceDatetime by RaceResults.raceDatetime
    var position by RaceResults.position
    var driverId by RaceResults.driverId
    var constructorId by RaceResults.constructorId
    var status by RaceResults.status
    var timeMillis by RaceResults.timeMillis
    var timeText by RaceResults.timeText
    var fastestLapRank by RaceResults.fastestLapRank
    var fastestLapLap by RaceResults.fastestLapLap
    var fastestLapTime by RaceResults.fastestLapTime
    var fastestLapAverageSpeedUnits by RaceResults.fastestLapAverageSpeedUnits
    var fastestLapAverageSpeedSpeed by RaceResults.fastestLapAverageSpeedSpeed
}
