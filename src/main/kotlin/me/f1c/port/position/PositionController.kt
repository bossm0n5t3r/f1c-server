package me.f1c.port.position

import me.f1c.domain.ResponseDto

interface PositionController {
    fun upToDate(sessionKey: Int): ResponseDto<Int>
}
