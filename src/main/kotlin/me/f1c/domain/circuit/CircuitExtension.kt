package me.f1c.domain.circuit

fun CircuitEntity.toDto(): CircuitDto {
    val circuitInfo = CircuitInfo.findByCircuitIdOrNull(circuitId)
    return CircuitDto(
        season = season,
        circuitId = circuitId,
        url = url,
        circuitName = circuitName,
        latitude = latitude,
        longitude = longitude,
        country = country,
        locality = locality,
        trackIconUrl = circuitInfo?.trackIconUrl,
        mapUrl = circuitInfo?.mapUrl,
    )
}
