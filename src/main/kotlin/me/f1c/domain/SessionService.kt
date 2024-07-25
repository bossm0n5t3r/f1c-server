package me.f1c.domain

import me.f1c.port.SessionRepository
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionRepository: SessionRepository,
) {
    fun findAll(): List<SessionDto> = sessionRepository.findAll()

    fun findBySessionKey(sessionKey: Int): SessionDto? = sessionRepository.findBySessionKey(sessionKey)

    fun findAllSessionKeys(): List<Int> = sessionRepository.findAllSessionKeys()
}
