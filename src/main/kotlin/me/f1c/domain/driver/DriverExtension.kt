package me.f1c.domain.driver

import me.f1c.domain.driver.DriverConstants.DRIVER_FULL_NAME_TO_KOREAN_DRIVER_NAME

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
        DRIVER_FULL_NAME_TO_KOREAN_DRIVER_NAME[fullName],
    )

fun OpenF1DriverDto.toDto() =
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
        null,
    )
