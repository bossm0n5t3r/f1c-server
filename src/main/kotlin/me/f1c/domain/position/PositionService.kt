package me.f1c.domain.position

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.POSITION_API
import me.f1c.port.position.PositionRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class PositionService(
    private val restClient: RestClient,
    private val positionRepository: PositionRepository,
) {
    fun upToDate(sessionKey: Int): Int =
        runCatching {
            if (positionRepository.findAllBySessionKey(sessionKey).isNotEmpty()) return@runCatching 0
            val rawResponse =
                restClient
                    .get()
                    .uri("$POSITION_API?session_key=$sessionKey")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val pitDtoList = objectMapper.readValue<List<OpenF1PositionDto>>(bodyString).map { it.toDto() }
            positionRepository.batchInsert(pitDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}, {}", sessionKey, it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()

    fun findAllBySessionKey(sessionKey: Int): List<PositionDto> =
        runCatching {
            positionRepository.findAllBySessionKey(sessionKey)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAllBySessionKey: {}, {}", sessionKey, it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAllBySessionKey: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()
}
