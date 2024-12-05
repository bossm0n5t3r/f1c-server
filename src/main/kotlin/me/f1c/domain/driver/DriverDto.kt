package me.f1c.domain.driver

import kotlinx.datetime.LocalDate

data class DriverDto(
    val season: Int,
    val driverId: String,
    val permanentNumber: Int?,
    val code: String?,
    val url: String?,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: LocalDate?,
    val nationality: String?,
    val fullNameKo: String? = null,
    val headshotUrl: String? = null,
)
