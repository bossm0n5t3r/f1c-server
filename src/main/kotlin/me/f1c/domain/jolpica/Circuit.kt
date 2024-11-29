package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class Circuit(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    @JsonProperty("Location")
    val location: Location,
)
