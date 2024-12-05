package me.f1c.domain.driver

import kotlinx.datetime.LocalDate
import me.f1c.domain.jolpica.Driver

fun DriverEntity.toDto(): DriverDto {
    val driverInfo = DriverInfo.findByDriverIdOrNull(driverId)
    return DriverDto(
        season,
        driverId,
        permanentNumber,
        code,
        url,
        givenName,
        familyName,
        dateOfBirth,
        nationality,
        driverInfo?.koreanDriverName,
        driverInfo?.headshotUrl,
    )
}

fun Driver.toDriverDto(season: Int) =
    DriverDto(
        season,
        driverId,
        permanentNumber?.toInt(),
        code,
        url,
        givenName,
        familyName,
        dateOfBirth?.let { LocalDate.parse(it) },
        nationality,
    )
