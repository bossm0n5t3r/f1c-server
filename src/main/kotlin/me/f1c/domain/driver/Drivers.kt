package me.f1c.domain.driver

import me.f1c.domain.BaseLongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Drivers : BaseLongIdTable("drivers") {
    val season = integer("season")
    val driverId = varchar("driver_id", 100)
    val permanentNumber = integer("permanent_number").nullable()
    val code = varchar("code", 10).nullable()
    val url = varchar("url", 500).nullable()
    val givenName = varchar("given_name", 100)
    val familyName = varchar("family_name", 100)
    val dateOfBirth = date("date_of_birth").nullable()
    val nationality = varchar("nationality", 100).nullable()
}
