package me.f1c.domain.driver

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
    val fullNameKo: String?,
) {
    fun validate(): Boolean = countryCode != null && firstName != null && lastName != null && teamColour != null && teamName != null

    fun updateFullNameKo(fullNameKo: String?): DriverDto = copy(fullNameKo = fullNameKo)
}
