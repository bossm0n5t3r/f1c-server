package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class RaceTable(
    val season: String,
    @JsonProperty("Races")
    val races: List<RaceDto>,
)
