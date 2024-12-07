package me.f1c.domain.constructor

enum class ConstructorInfo(
    val constructorId: String,
    val hexColor: String,
) {
    ALPINE("alpine", "#0093cc"),
    ASTON_MARTIN("aston_martin", "#229971"),
    FERRARI("ferrari", "#e80020"),
    HAAS("haas", "#b6babd"),
    MCLAREN("mclaren", "#ff8000"),
    MERCEDES("mercedes", "#27f4d2"),
    RB("rb", "#6692ff"),
    RED_BULL("red_bull", "#3671c6"),
    SAUBER("sauber", "#52e252"),
    WILLIAMS("williams", "#64c4ff"),
    ;

    companion object {
        fun findByConstructorIdOrNull(constructorId: String): ConstructorInfo? = entries.find { it.constructorId == constructorId }
    }
}
