package me.f1c.domain.schedule

import com.fasterxml.jackson.annotation.JsonProperty

data class JolpicaF1RaceResponseDto(
    @JsonProperty("MRData")
    val mrData: MRData,
) {
    data class MRData(
        val xmlns: String,
        val series: String,
        val url: String,
        val limit: String,
        val offset: String,
        val total: String,
        @JsonProperty("RaceTable")
        val raceTable: RaceTable,
    ) {
        data class RaceTable(
            val season: String,
            @JsonProperty("Races")
            val races: List<JolpicaF1RaceDto>,
        )
    }
}
