package me.f1c.domain.lap

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.LAP_API
import me.f1c.port.lap.LapRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class LapService(
    private val restClient: RestClient,
    private val lapRepository: LapRepository,
) {
    fun upToDate(sessionKey: Int): Int =
        runCatching {
            if (lapRepository.findAllBySessionKey(sessionKey).isNotEmpty()) return@runCatching 0
            val rawResponse =
                restClient
                    .get()
                    .uri("$LAP_API?session_key=$sessionKey")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val lapDtoList = objectMapper.readValue<List<OpenF1LapDto>>(bodyString).map { it.toDto() }
            lapRepository.batchInsert(lapDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}, {}", sessionKey, it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()
}
