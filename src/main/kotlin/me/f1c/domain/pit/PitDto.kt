package me.f1c.domain.pit

import java.math.BigDecimal

data class PitDto(
    val sessionKey: Int,
    val meetingKey: Int,
    val date: String,
    val driverNumber: Int,
    val pitDuration: BigDecimal,
    val lapNumber: Int,
)
