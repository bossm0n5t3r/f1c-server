package me.f1c.domain.driver

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.DRIVER_API
import me.f1c.port.driver.DriverRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class DriverService(
    private val restClient: RestClient,
    private val driverRepository: DriverRepository,
) {
    fun findAll(sessionKey: Int): List<DriverDto> =
        runCatching {
            driverRepository.findAllBySessionKey(sessionKey)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAll: {}, {}", sessionKey, it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAll: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()

    fun upToDate(sessionKey: Int): Int =
        runCatching {
            if (driverRepository.findAllBySessionKey(sessionKey).isNotEmpty()) return@runCatching 0
            val rawResponse =
                restClient
                    .get()
                    .uri("$DRIVER_API?session_key=$sessionKey")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val driverDtoList = objectMapper.readValue<List<OpenF1DriverDto>>(bodyString).map { it.toDto() }
            driverRepository.batchInsert(driverDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}, {}", sessionKey, it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()
}
