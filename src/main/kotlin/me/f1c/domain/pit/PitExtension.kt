package me.f1c.domain.pit

fun PitEntity.toDto() =
    PitDto(
        sessionKey,
        meetingKey,
        date.toString(),
        driverNumber,
        pitDuration,
        lapNumber,
    )

fun OpenF1PitDto.toDto() =
    PitDto(
        sessionKey,
        meetingKey,
        date,
        driverNumber,
        pitDuration,
        lapNumber,
    )
