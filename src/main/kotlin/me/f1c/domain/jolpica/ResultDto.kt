package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultDto(
    val number: String,
    val position: String,
    val positionText: String,
    val points: String,
    @JsonProperty("Driver")
    val driver: Driver,
    @JsonProperty("Constructor")
    val constructor: Constructor,
    val grid: String? = null,
    val laps: String? = null,
    val status: String,
    @JsonProperty("Time")
    val time: Time? = null,
    @JsonProperty("FastestLap")
    val fastestLap: FastestLap,
)
