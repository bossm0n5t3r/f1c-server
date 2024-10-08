package me.f1c.domain.session

fun SessionEntity.toDto() =
    SessionDto(
        sessionKey,
        sessionName,
        dateStart.toString(),
        dateEnd.toString(),
        gmtOffset,
        sessionType,
        meetingKey,
        location,
        countryCode,
        countryKey,
        countryName,
        circuitKey,
        circuitShortName,
        year,
    )

fun OpenF1SessionDto.toDto() =
    SessionDto(
        sessionKey,
        sessionName,
        dateStart,
        dateEnd,
        gmtOffset,
        sessionType,
        meetingKey,
        location,
        countryCode,
        countryKey,
        countryName,
        circuitKey,
        circuitShortName,
        year,
    )
