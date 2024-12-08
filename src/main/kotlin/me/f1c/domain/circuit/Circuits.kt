package me.f1c.domain.circuit

import me.f1c.domain.BaseLongIdTable

object Circuits : BaseLongIdTable("circuits") {
    val season = integer("season")
    val circuitId = varchar("circuit_id", 100)
    val url = varchar("url", 500)
    val circuitName = varchar("circuit_name", 200)
    val latitude = varchar("latitude", 100)
    val longitude = varchar("longitude", 100)
    val country = varchar("country", 100)
    val locality = varchar("locality", 100)
}
