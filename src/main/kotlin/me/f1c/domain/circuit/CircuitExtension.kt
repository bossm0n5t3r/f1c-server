package me.f1c.domain.circuit

import me.f1c.domain.jolpica.Circuit

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

fun Circuit.toCircuitDto(season: Int): CircuitDto =
    CircuitDto(
        season = season,
        circuitId = circuitId,
        url = url,
        circuitName = circuitName,
        latitude = this.location.lat,
        longitude = this.location.long,
        country = this.location.country,
        locality = this.location.locality,
    )
