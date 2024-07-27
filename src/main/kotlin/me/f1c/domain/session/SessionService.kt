package me.f1c.domain.session

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.SESSION_API
import me.f1c.port.session.SessionRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class SessionService(
    private val restClient: RestClient,
    private val sessionRepository: SessionRepository,
) {
    fun upToDate() =
        runCatching {
            val rawResponse =
                restClient
                    .get()
                    .uri("${SESSION_API}?year=2024")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val sessionResponseDtoList = objectMapper.readValue<List<SessionResponseDto>>(bodyString)
            val allSessionKeys = this.findAllSessionKeys().toSet()
            val filteredSessionResponseDtoList =
                sessionResponseDtoList
                    .filterNot { allSessionKeys.contains(it.sessionKey) }
            sessionRepository.batchInsert(filteredSessionResponseDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}", it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, ", it.message, it)
        }.getOrThrow()

    fun findAll(): List<SessionDto> =
        runCatching {
            sessionRepository.findAll()
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAll: {}", it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAll: {}, ", it.message, it)
        }.getOrThrow()

    fun findBySessionKey(sessionKey: Int): SessionDto? =
        runCatching {
            sessionRepository.findBySessionKey(sessionKey)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findBySessionKey: {}", sessionKey)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findBySessionKey: {}, ", it.message, it)
        }.getOrThrow()

    fun findAllSessionKeys(): List<Int> =
        runCatching {
            sessionRepository.findAllSessionKeys()
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAllSessionKeys: {}", it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAllSessionKeys: {}, ", it.message, it)
        }.getOrThrow()
}
