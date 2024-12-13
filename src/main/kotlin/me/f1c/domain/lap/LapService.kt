package me.f1c.domain.lap

import me.f1c.adapter.external.JolpicaF1ClientImpl
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.JolpicaF1ResponseDto
import me.f1c.domain.jolpica.MRDataWithRaceTable
import me.f1c.port.external.callGet
import me.f1c.port.lap.LapRepository
import org.springframework.stereotype.Service

@Service
class LapService(
    private val jolpicaF1Client: JolpicaF1ClientImpl,
    private val lapRepository: LapRepository,
) {
    fun upToDate(
        season: Int,
        round: Int,
    ): Int {
        fun Int.withSucceededInfoLog() = this.also { LOGGER.info("{} upToDate: {}, {}, {}", LogResult.SUCCEEDED, season, round, it) }

        return try {
            if (lapRepository.findAllBySeasonAndRound(season, round).isNotEmpty()) {
                return 0.withSucceededInfoLog()
            }

            val totalLapDtoList = mutableListOf<LapDto>()

            var lapNumber = 1
            while (true) {
                val lapApi = jolpicaF1Client.getLapApi(season, round, lapNumber)
                val lapResponseDto =
                    requireNotNull(
                        jolpicaF1Client.callGet<JolpicaF1ResponseDto<MRDataWithRaceTable>>(lapApi),
                    ) { "JolpicaF1ResponseDto<MRDataWithRaceTable> does not exist" }

                val lapDtoList =
                    lapResponseDto
                        .mrData
                        .raceTable
                        .races
                        .firstOrNull()
                        ?.toLapDto()
                        ?: break
                totalLapDtoList.add(lapDtoList)
                lapNumber++
            }

            lapRepository
                .batchInsert(totalLapDtoList)
                .withSucceededInfoLog()
        } catch (e: Exception) {
            LOGGER.error("{} upToDate: {}, {}, {}, ", LogResult.FAILED, season, round, e.message, e)
            throw e
        }
    }
}
