package me.f1c.port

import me.f1c.domain.SessionDto

interface SessionRepository {
    fun findAll(): List<SessionDto>

    fun findBySessionKey(sessionKey: Int): SessionDto?

    fun findAllSessionKeys(): List<Int>
}
