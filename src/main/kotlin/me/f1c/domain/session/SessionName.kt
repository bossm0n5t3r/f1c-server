package me.f1c.domain.session

enum class SessionName(
    val searchValue: String,
) {
    RACE("Race"),
    PRACTICE_1("Practice 1"),
    PRACTICE_2("Practice 2"),
    PRACTICE_3("Practice 3"),
    QUALIFYING("Qualifying"),
    SPRINT_QUALIFYING("Sprint Qualifying"),
    SPRINT("Sprint"),
    ;

    companion object {
        fun fromSearchValue(searchValue: String): SessionName = requireNotNull(SessionName.entries.find { it.searchValue == searchValue })
    }
}
