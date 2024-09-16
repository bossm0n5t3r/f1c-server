package me.f1c.domain.session

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.SESSION_API
import me.f1c.exception.F1CBadRequestException
import me.f1c.port.session.SessionRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.time.LocalDate

@Service
class SessionService(
    private val restClient: RestClient,
    private val sessionRepository: SessionRepository,
) {
    fun upToDate() =
        runCatching {
            val thisYear = LocalDate.now().year
            val rawResponse =
                restClient
                    .get()
                    .uri("${SESSION_API}?year=$thisYear")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val sessionDtoList = objectMapper.readValue<List<OpenF1SessionDto>>(bodyString).map { it.toDto() }
            val allSessionKeys = this.findAllSessionKeys().toSet()
            val filteredSessionDtoList =
                sessionDtoList
                    .filterNot { allSessionKeys.contains(it.sessionKey) }
            sessionRepository.batchInsert(filteredSessionDtoList)
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
            LOGGER.error("${LogResult.FAILED.name} findBySessionKey: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()

    fun findAllSessionKeys(): List<Int> =
        runCatching {
            sessionRepository.findAllSessionKeys()
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAllSessionKeys: {}", it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAllSessionKeys: {}, ", it.message, it)
        }.getOrThrow()

    fun getLatest(rawSessionName: String?): SessionDto? =
        try {
            val sessionName = validateRawSessionName(rawSessionName)

            val result =
                if (sessionName == null) {
                    sessionRepository.findLatestSession()
                } else {
                    sessionRepository.findLatestSession(sessionName)
                }

            LOGGER.info("${LogResult.SUCCEEDED.name} getLatest: {}, {}, {}", rawSessionName, sessionName, result)
            result
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED.name} getLatest: {}, {}, ", rawSessionName, e.message, e)
            throw e
        }

    private fun validateRawSessionName(rawSessionName: String?): SessionName? =
        try {
            rawSessionName?.let { SessionName.fromSearchValue(it) }
        } catch (e: Exception) {
            throw F1CBadRequestException("Invalid sessionName Request Parameter", "sessionName" to rawSessionName)
        }
}
