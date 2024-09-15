package me.f1c.port.session

import me.f1c.domain.session.SessionDto
import me.f1c.domain.session.SessionName

interface SessionRepository {
    fun findAll(): List<SessionDto>

    fun findBySessionKey(sessionKey: Int): SessionDto?

    fun findAllSessionKeys(): List<Int>

    fun batchInsert(sessionDtoList: List<SessionDto>): Int

    fun findLatestSession(): SessionDto?

    fun findLatestSession(sessionName: SessionName): SessionDto?
}
