package me.f1c.domain.position

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenF1PositionDto(
    val date: String,
    val meetingKey: Int,
    val sessionKey: Int,
    val driverNumber: Int,
    val position: Int,
)
