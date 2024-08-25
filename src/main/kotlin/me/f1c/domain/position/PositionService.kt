package me.f1c.domain.position

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.POSITION_API
import me.f1c.domain.driver.DriverDto
import me.f1c.port.driver.DriverRepository
import me.f1c.port.position.PositionRepository
import me.f1c.util.ObjectMapperUtil.objectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class PositionService(
    private val restClient: RestClient,
    private val positionRepository: PositionRepository,
    private val driverRepository: DriverRepository,
) {
    fun upToDate(sessionKey: Int): Int =
        runCatching {
            val rawResponse =
                restClient
                    .get()
                    .uri("$POSITION_API?session_key=$sessionKey")
                    .retrieve()
                    .toEntity(String::class.java)
            val bodyString = rawResponse.body ?: error("Body does not exist")
            val pitDtoList = objectMapper.readValue<List<PositionDto>>(bodyString)
            positionRepository.batchInsert(pitDtoList)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} upToDate: {}, {}", sessionKey, it)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} upToDate: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()

    fun rankings(sessionKey: Int): List<DriverDto> =
        try {
            val drivers =
                driverRepository
                    .findAll(sessionKey)
                    .takeIf { it.isNotEmpty() }
                    ?: error("Drivers is empty")

            val positions =
                positionRepository
                    .findAllBySessionKey(sessionKey)
                    .takeIf { it.isNotEmpty() }
                    ?: error("Positions is empty")

            val driverNumberToDriver = drivers.associateBy { it.driverNumber }

            positions
                .groupBy { it.driverNumber }
                .values
                .mapNotNull { positionsGroupByDriverNumber ->
                    positionsGroupByDriverNumber.maxByOrNull { it.dataAsLocalDateTime }
                }.sortedBy { it.position }
                .mapNotNull { driverNumberToDriver[it.driverNumber] }
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED.name} rankings: {}, {}, ", sessionKey, e.message, e)
            throw e
        }
}
