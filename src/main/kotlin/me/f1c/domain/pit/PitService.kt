package me.f1c.domain.pit

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.PIT_API
import me.f1c.port.pit.PitRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class PitService(
    private val restClient: RestClient,
    private val pitRepository: PitRepository,
) {
    fun upToDate(sessionKey: Int): Int =
        runCatching {
            val rawResponse =
                restClient
                    .get()
                    .uri("$PIT_API?session_key=$sessionKey")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val pitDtoList = objectMapper.readValue<List<OpenF1PitDto>>(bodyString).map { it.toDto() }
            pitRepository.batchInsert(pitDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}, {}", sessionKey, it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()
}
