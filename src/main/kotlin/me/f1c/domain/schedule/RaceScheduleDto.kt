package me.f1c.domain.schedule

data class RaceScheduleDto(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuitId: String,
    val circuitName: String,
    val raceType: String,
    val raceDatetime: String,
)
