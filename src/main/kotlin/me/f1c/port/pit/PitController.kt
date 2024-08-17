package me.f1c.port.pit

interface PitController {
    fun upToDate(sessionKey: Int): Int
}
