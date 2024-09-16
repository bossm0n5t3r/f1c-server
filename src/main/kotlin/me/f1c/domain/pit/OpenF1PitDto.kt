package me.f1c.domain.pit

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OpenF1PitDto(
    val sessionKey: Int,
    val meetingKey: Int,
    val date: String,
    val driverNumber: Int,
    val pitDuration: BigDecimal,
    val lapNumber: Int,
)
