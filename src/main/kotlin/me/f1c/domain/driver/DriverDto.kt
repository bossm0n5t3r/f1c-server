package me.f1c.domain.driver

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DriverDto(
    val driverNumber: Int,
    val sessionKey: Int,
    val meetingKey: Int,
    val broadcastName: String,
    val countryCode: String?,
    val firstName: String?,
    val lastName: String?,
    val fullName: String,
    val headshotUrl: String?,
    val teamColour: String?,
    val teamName: String?,
    val nameAcronym: String,
)
