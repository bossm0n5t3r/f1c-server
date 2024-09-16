package me.f1c.domain.session

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenF1SessionDto(
    val sessionKey: Int,
    val sessionName: String,
    val dateStart: String,
    val dateEnd: String,
    val gmtOffset: String,
    val sessionType: String,
    val meetingKey: Int,
    val location: String,
    val countryCode: String,
    val countryKey: Int,
    val countryName: String,
    val circuitKey: Int,
    val circuitShortName: String,
    val year: Int,
)
