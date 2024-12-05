package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class DriverTable(
    val season: String,
    @JsonProperty("Drivers")
    val drivers: List<Driver>,
)
