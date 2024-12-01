package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class RaceTable(
    val season: String,
    val round: String? = null,
    @JsonProperty("Races")
    val races: List<RaceDto>,
)
