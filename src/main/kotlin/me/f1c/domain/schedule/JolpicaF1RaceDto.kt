package me.f1c.domain.schedule

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class JolpicaF1RaceDto(
    val season: String,
    val round: String,
    val url: String,
    val raceName: String,
    @JsonProperty("Circuit")
    val circuit: Circuit,
    val date: String,
    val time: String? = null,
    @JsonProperty("FirstPractice")
    val firstPractice: DateTime? = null,
    @JsonProperty("SecondPractice")
    val secondPractice: DateTime? = null,
    @JsonProperty("ThirdPractice")
    val thirdPractice: DateTime? = null,
    @JsonProperty("Qualifying")
    val qualifying: DateTime? = null,
    @JsonProperty("Sprint")
    val sprint: DateTime? = null,
    @JsonProperty("SprintQualifying")
    val sprintQualifying: DateTime? = null,
    @JsonProperty("SprintShootout")
    val sprintShootout: DateTime? = null,
) {
    data class Circuit(
        val circuitId: String,
        val url: String,
        val circuitName: String,
        @JsonProperty("Location")
        val location: Location,
    ) {
        data class Location(
            val lat: String,
            val long: String,
            val locality: String,
            val country: String,
        )
    }

    data class DateTime(
        val date: String? = null,
        val time: String? = null,
    )
}
