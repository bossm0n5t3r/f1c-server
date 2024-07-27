package me.f1c.domain.session

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.OffsetDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SessionResponseDto(
    val sessionKey: Int,
    val sessionName: String? = null,
    val dateStart: OffsetDateTime? = null,
    val dateEnd: OffsetDateTime? = null,
    val gmtOffset: String? = null,
    val sessionType: String? = null,
    val meetingKey: Int,
    val location: String,
    val countryCode: String? = null,
    val countryKey: Int,
    val countryName: String? = null,
    val circuitKey: Int,
    val circuitShortName: String? = null,
    val year: Int,
)
