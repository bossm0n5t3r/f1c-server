package me.f1c.domain.jolpica

data class Driver(
    val driverId: String,
    val permanentNumber: String? = null,
    val code: String? = null,
    val url: String? = null,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String? = null,
    val nationality: String? = null,
)
