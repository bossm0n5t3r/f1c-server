package me.f1c.domain.session

data class SessionDto(
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
