package me.f1c.domain.position

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import kotlinx.datetime.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PositionDto(
    val date: String,
    val meetingKey: Int,
    val sessionKey: Int,
    val driverNumber: Int,
    val position: Int,
) {
    val dataAsLocalDateTime: LocalDateTime by lazy {
        LocalDateTime.parse(date)
    }
}
