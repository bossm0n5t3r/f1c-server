package me.f1c.domain.constructor

import me.f1c.domain.BaseLongIdTable

object Constructors : BaseLongIdTable("constructors") {
    val season = integer("season")
    val constructorId = varchar("constructor_id", 100)
    val url = varchar("url", 500).nullable()
    val name = varchar("name", 100)
    val nationality = varchar("nationality", 100).nullable()
}
