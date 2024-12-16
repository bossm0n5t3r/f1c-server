package me.f1c.domain.schedule

enum class RaceType(
    val value: String,
    val order: Int,
) {
    FIRST_PRACTICE("FirstPractice", 0),
    SECOND_PRACTICE("SecondPractice", 1),
    THIRD_PRACTICE("ThirdPractice", 2),
    QUALIFYING("Qualifying", 3),
    SPRINT_QUALIFYING("SprintQualifying", 4),
    SPRINT("Sprint", 5),
    RACE("Race", 6),
    ;

    companion object {
        fun findByValueOrNull(value: String): RaceType? = entries.find { it.value == value }
    }
}
