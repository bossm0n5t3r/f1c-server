package me.f1c.port.session

import me.f1c.domain.ResponseDto
import me.f1c.domain.session.SessionDto

interface SessionController {
    fun upToDate(): ResponseDto<Int>

    fun findAll(): ResponseDto<List<SessionDto>>

    fun findBySessionKey(sessionKey: Int): ResponseDto<SessionDto?>

    fun findAllSessionKeys(): ResponseDto<List<Int>>

    fun getLatest(sessionName: String?): ResponseDto<SessionDto?>
}
