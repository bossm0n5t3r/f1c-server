package me.f1c.adapter

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.domain.SessionDto
import me.f1c.port.SessionRepository
import me.f1c.util.FileUtil.readText
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Repository

@Repository
class SessionRepositoryImpl : SessionRepository {
    override fun findAll(): List<SessionDto> {
        return objectMapper.readValue<List<SessionDto>>("sessions.json".readText())
    }

    override fun findBySessionKey(sessionKey: Int): SessionDto? {
        return this.findAll().find { it.sessionKey == sessionKey }
    }

    override fun findAllSessionKeys(): List<Int> {
        return this.findAll().map { it.sessionKey }
    }
}
