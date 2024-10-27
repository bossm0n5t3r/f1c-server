package me.f1c.domain.position

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.OpenF1API.POSITION_API
import me.f1c.domain.driver.DriverConstants.DRIVER_FULL_NAME_TO_KOREAN_DRIVER_NAME
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

    fun rankings(sessionKey: Int): List<DriverDto> =
        try {
            val drivers =
                driverRepository
                    .findAllBySessionKey(sessionKey)
                    .takeIf { it.isNotEmpty() }
                    ?: error("Drivers is empty")

            val positions =
                positionRepository
                    .findAllBySessionKey(sessionKey)
                    .takeIf { it.isNotEmpty() }
                    ?: error("Positions is empty")

            val driverNumberToDriver =
                drivers
                    .associateBy { it.driverNumber }
                    .validateAndRefresh()

            positions
                .groupBy { it.driverNumber }
                .values
                .mapNotNull { positionsGroupByDriverNumber ->
                    positionsGroupByDriverNumber.maxByOrNull { it.dataAsLocalDateTime }
                }.sortedBy { it.position }
                .mapNotNull { driverNumberToDriver[it.driverNumber] }
                .also { LOGGER.info("${LogResult.SUCCEEDED.name} rankings: {}, {}", sessionKey, it.size) }
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED.name} rankings: {}, {}, ", sessionKey, e.message, e)
            throw e
        }

    private fun Map<Int, DriverDto>.validateAndRefresh(): Map<Int, DriverDto> =
        mapValues { (_, driverDto) ->
            driverDto.takeIf { it.validate() } ?: getValidatedDriver(driverDto.driverNumber)
        }

    private fun getValidatedDriver(driverNumber: Int): DriverDto =
        driverRepository
            .findAllByDriverNumberOrderBySessionKey(driverNumber)
            .firstOrNull { it.validate() }
            ?.let { it.updateFullNameKo(DRIVER_FULL_NAME_TO_KOREAN_DRIVER_NAME[it.fullName]) }
            ?: error("Not found validated driver: $driverNumber")

    fun findAllBySessionKey(sessionKey: Int): List<PositionDto> =
        runCatching {
            positionRepository.findAllBySessionKey(sessionKey)
        }.onSuccess {
            LOGGER.info("${LogResult.SUCCEEDED.name} findAllBySessionKey: {}, {}", sessionKey, it.size)
        }.onFailure {
            LOGGER.error("${LogResult.FAILED.name} findAllBySessionKey: {}, {}, ", sessionKey, it.message, it)
        }.getOrThrow()
}
