package me.f1c.domain.jolpica

import com.fasterxml.jackson.annotation.JsonProperty

data class MRDataWithDriverTable(
    val xmlns: String,
    val series: String,
    val url: String,
    val limit: String,
    val offset: String,
    val total: String,
    @JsonProperty("DriverTable")
    val driverTable: DriverTable,
)
