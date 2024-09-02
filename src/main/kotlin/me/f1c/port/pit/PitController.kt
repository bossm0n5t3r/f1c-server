package me.f1c.port.pit

import me.f1c.domain.ResponseDto

interface PitController {
    fun upToDate(sessionKey: Int): ResponseDto<Int>
}
