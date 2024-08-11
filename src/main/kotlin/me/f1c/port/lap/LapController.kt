package me.f1c.port.lap

interface LapController {
    fun upToDate(sessionKey: Int): Int
}
