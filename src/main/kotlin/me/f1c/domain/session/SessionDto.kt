package me.f1c.domain.session

import kotlinx.datetime.LocalDateTime

data class SessionDto(
    val sessionKey: Int,
    val sessionName: String? = null,
    val dateStart: LocalDateTime? = null,
    val dateEnd: LocalDateTime? = null,
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
