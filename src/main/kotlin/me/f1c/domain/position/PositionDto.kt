package me.f1c.domain.position

import kotlinx.datetime.LocalDateTime

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
