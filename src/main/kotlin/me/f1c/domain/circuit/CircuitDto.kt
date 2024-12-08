package me.f1c.domain.circuit

data class CircuitDto(
    val season: Int,
    val circuitId: String,
    val url: String,
    val circuitName: String,
    val latitude: String,
    val longitude: String,
    val country: String,
    val locality: String,
    val trackIconUrl: String? = null,
    val mapUrl: String? = null,
)
