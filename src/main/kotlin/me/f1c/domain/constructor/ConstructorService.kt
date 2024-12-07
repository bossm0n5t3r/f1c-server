package me.f1c.domain.constructor

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithConstructorTable
import me.f1c.port.constructor.ConstructorRepository
import me.f1c.port.external.callGet
import me.f1c.util.DateTimeUtil.SERVER_TIME_ZONE
import org.springframework.stereotype.Service

@Service
class ConstructorService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val constructorRepository: ConstructorRepository,
) {
    fun upToDate(): Int {
        fun Int.withSucceededInfoLog() = this.also { LOGGER.info("{} upToDate: {}", LogResult.SUCCEEDED, it) }

        return try {
            val now =
                Clock.System
                    .now()
                    .toLocalDateTime(SERVER_TIME_ZONE)
            val year = now.year
            val constructorApi = jolpicaF1Client.getConstructorApi(year)
            val constructorResponseDto =
                requireNotNull(
                    jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithConstructorTable>>(constructorApi),
                ) { "JolpicaF1ResponseDto<MRDataWithConstructorTable> does not exist" }
            val constructorDtoList =
                constructorResponseDto
                    .mrData
                    .constructorTable
                    .constructors
                    .map { it.toConstructorDto(year) }
            constructorRepository.batchInsert(constructorDtoList).withSucceededInfoLog()
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}, ", LogResult.FAILED, e.message, e)
            throw e
        }
    }

    fun findAllBySeason(season: Int): List<ConstructorDto> =
        try {
            constructorRepository
                .findAllBySeason(season)
                .also { LOGGER.info("{} findAll: {}, {}", LogResult.SUCCEEDED, season, it.size) }
        } catch (e: Exception) {
            LOGGER.error("{} findAll: {}, {}, ", LogResult.FAILED, season, e.message, e)
            throw e
        }
}
