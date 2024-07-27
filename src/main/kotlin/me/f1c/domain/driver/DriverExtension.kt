package me.f1c.domain.driver

fun DriverEntity.toDto() =
    DriverDto(
        driverNumber,
        sessionKey,
        meetingKey,
        broadcastName,
        countryCode,
        firstName,
        lastName,
        fullName,
        headshotUrl,
        teamColour,
        teamName,
        nameAcronym,
    )
