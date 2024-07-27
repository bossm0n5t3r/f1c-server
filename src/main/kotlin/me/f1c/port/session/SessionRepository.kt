package me.f1c.port.session

import me.f1c.domain.session.SessionDto
import me.f1c.domain.session.SessionResponseDto

interface SessionRepository {
    fun findAll(): List<SessionDto>

    fun findBySessionKey(sessionKey: Int): SessionDto?

    fun findAllSessionKeys(): List<Int>

    fun batchInsert(sessionResponseDtoList: List<SessionResponseDto>): Int
}
