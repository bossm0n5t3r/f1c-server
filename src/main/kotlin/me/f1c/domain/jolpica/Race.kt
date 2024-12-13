package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Race(
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
    @JsonProperty("Results")
    val results: List<Result>? = null,
    @JsonProperty("Laps")
    val laps: List<Lap>? = null,
)
