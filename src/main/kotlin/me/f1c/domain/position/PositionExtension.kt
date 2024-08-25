package me.f1c.domain.position

fun PositionEntity.toDto() =
    PositionDto(
        date.toString(),
        meetingKey,
        sessionKey,
        driverNumber,
        position,
    )
