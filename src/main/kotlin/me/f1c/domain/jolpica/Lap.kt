package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class Lap(
    val number: String,
    @JsonProperty("Timings")
    val timings: List<LapTiming>,
)
