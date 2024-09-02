package me.f1c.port.lap

import me.f1c.domain.ResponseDto

interface LapController {
    fun upToDate(sessionKey: Int): ResponseDto<Int>
}
