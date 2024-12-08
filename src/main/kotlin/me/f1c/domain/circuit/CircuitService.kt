package me.f1c.domain.circuit

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithCircuitTable
import me.f1c.port.circuit.CircuitRepository
import me.f1c.port.external.callGet
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import org.springframework.stereotype.Service

@Service
class CircuitService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val circuitRepository: CircuitRepository,
) {
    fun upToDate(): Int {
        fun Int.withSucceededInfoLog() = this.also { LOGGER.info("{} upToDate: {}", LogResult.SUCCEEDED, it) }

        return try {
            val now =
                Clock.System
                    .now()
                    .toLocalDateTime(SERVER_TIME_ZONE)
            val year = now.year
            val circuitApi = jolpicaF1Client.getCircuitApi(year)
            val circuitResponseDto =
                requireNotNull(
                    jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithCircuitTable>>(circuitApi),
                ) { "JolpicaF1ResponseDto<MRDataWithCircuitTable> does not exist" }
            val circuitDtoList =
                circuitResponseDto
                    .mrData
                    .circuitTable
                    .circuits
                    .map { it.toCircuitDto(year) }
            circuitRepository.batchInsert(circuitDtoList).withSucceededInfoLog()
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}", LogResult.FAILED, e.message, e)
            throw e
        }
    }

    fun findAllBySeason(season: Int): List<CircuitDto> =
        try {
            circuitRepository
                .findAllBySeason(season)
                .also { LOGGER.info("{} findAll: {}, {}", LogResult.SUCCEEDED, season, it.size) }
        } catch (e: Exception) {
            LOGGER.error("{} findAll: {}, {}", LogResult.FAILED, season, e.message, e)
            throw e
        }
}
