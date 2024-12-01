package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class FastestLap(
    val rank: String,
    val lap: String,
    @JsonProperty("Time")
    val time: Time,
    @JsonProperty("AverageSpeed")
    val averageSpeed: AverageSpeed,
)
