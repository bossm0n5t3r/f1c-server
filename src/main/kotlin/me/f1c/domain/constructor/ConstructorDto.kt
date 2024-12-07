package me.f1c.domain.constructor

data class ConstructorDto(
    val season: Int,
    val constructorId: String,
    val url: String?,
    val name: String,
    val nationality: String?,
    val color: String? = null,
)
