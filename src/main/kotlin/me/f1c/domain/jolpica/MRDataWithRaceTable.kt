package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class MRDataWithRaceTable(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @JsonProperty("RaceTable")
    val raceTable: RaceTable,
)
