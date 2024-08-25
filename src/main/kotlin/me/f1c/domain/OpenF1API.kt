package me.f1c.domain

object OpenF1API {
    private const val OPEN_F1_API = "https://api.openf1.org/v1"

    const val SESSION_API = "${OPEN_F1_API}/sessions"
    const val DRIVER_API = "${OPEN_F1_API}/drivers"
    const val LAP_API = "${OPEN_F1_API}/laps"
    const val PIT_API = "${OPEN_F1_API}/pit"
    const val POSITION_API = "${OPEN_F1_API}/position"
}
