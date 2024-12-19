package me.f1c.domain.driver

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithDriverTable
import me.f1c.port.driver.DriverRepository
import me.f1c.port.external.callGet
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import org.springframework.stereotype.Service

@Service
class DriverService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val driverRepository: DriverRepository,
) {
    fun findAllBySeason(season: Int): List<DriverDto> =
        try {
            driverRepository
                .findAllBySeason(season)
                .also { LOGGER.info("{} findAll: {}, {}", LogResult.SUCCEEDED, season, it.size) }
        } catch (e: Exception) {
            LOGGER.error("{} findAll: {}, {}, ", LogResult.FAILED, season, e.message, e)
            throw e
        }

    fun upToDate(): Int =
        try {
            val now =
                Clock.System
                    .now()
                    .toLocalDateTime(SERVER_TIME_ZONE)
            val year = now.year
            val currentDriverIds = driverRepository.findAllBySeason(year).map { it.driverId }.toSet()
            val driverApi = jolpicaF1Client.getDriverApi(year)
            val driverResponseDto =
                requireNotNull(
                    jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithDriverTable>>(driverApi),
                ) { "JolpicaF1ResponseDto<MRDataWithDriverTable> does not exist" }
            val driverDtoList =
                driverResponseDto
                    .mrData
                    .driverTable
                    .drivers
                    .map { it.toDriverDto(year) }
                    .filterNot { currentDriverIds.contains(it.driverId) }
                    .sortedBy { it.driverId }
            driverRepository.batchInsert(driverDtoList).also { LOGGER.info("{} upToDate: {}", LogResult.SUCCEEDED, it) }
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}, ", LogResult.FAILED, e.message, e)
            throw e
        }
}
