package me.f1c.domain.lap

fun LapEntity.toDto() =
    LapDto(
        meetingKey,
        sessionKey,
        driverNumber,
        i1Speed,
        i2Speed,
        stSpeed,
        dateStart.toString(),
        lapDuration,
        isPitOutLap,
        durationSector1,
        durationSector2,
        durationSector3,
        segmentsSector1List.takeIf { it.isNotBlank() }?.split(", ")?.map { it.takeIf { it != "null" }?.toInt() } ?: emptyList(),
        segmentsSector2List.takeIf { it.isNotBlank() }?.split(", ")?.map { it.takeIf { it != "null" }?.toInt() } ?: emptyList(),
        segmentsSector3List.takeIf { it.isNotBlank() }?.split(", ")?.map { it.takeIf { it != "null" }?.toInt() } ?: emptyList(),
        lapNumber,
    )

fun OpenF1LapDto.toDto() =
    LapDto(
        meetingKey,
        sessionKey,
        driverNumber,
        i1Speed,
        i2Speed,
        stSpeed,
        dateStart,
        lapDuration,
        isPitOutLap,
        durationSector1,
        durationSector2,
        durationSector3,
        segmentsSector1,
        segmentsSector2,
        segmentsSector3,
        lapNumber,
    )
