package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class ConstructorTable(
    val season: String,
    @JsonProperty("Constructors")
    val constructors: List<Constructor>,
)
