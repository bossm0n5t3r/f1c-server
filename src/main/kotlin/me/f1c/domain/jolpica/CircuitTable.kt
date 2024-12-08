package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class CircuitTable(
    val season: String,
    @JsonProperty("Circuits")
    val circuits: List<Circuit>,
)
