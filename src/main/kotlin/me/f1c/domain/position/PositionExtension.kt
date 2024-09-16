package me.f1c.domain.position

fun PositionEntity.toDto() =
    PositionDto(
        date.toString(),
        meetingKey,
        sessionKey,
        driverNumber,
        position,
    )

fun OpenF1PositionDto.toDto() =
    PositionDto(
        date,
        meetingKey,
        sessionKey,
        driverNumber,
        position,
    )
