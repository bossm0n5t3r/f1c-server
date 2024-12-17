package me.f1c.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.jolpica.SeasonAndRound
import me.f1c.domain.schedule.RaceScheduleService
import me.f1c.util.Constants.END_ROUND
import org.springframework.stereotype.Component

@Component
class UpToDateHelper(
    private val raceScheduleService: RaceScheduleService,
) {
    fun <T> upToDate(
        now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
        startSeasonAndRound: SeasonAndRound,
        action: (seasonAndRound: SeasonAndRound, now: LocalDateTime) -> T,
    ): Pair<SeasonAndRound, List<T>> {
        val latestFinishedRaceSchedule = raceScheduleService.findLatestFinished()
        val latestFinishedSeasonAndRound = latestFinishedRaceSchedule?.toSeasonAndRound()
        if (latestFinishedSeasonAndRound != null && latestFinishedSeasonAndRound < startSeasonAndRound) {
            LOGGER.info(
                "{} upToDate: {}, {}, {}",
                LogResult.SUCCEEDED,
                "Already up-to-date",
                startSeasonAndRound,
                latestFinishedSeasonAndRound,
            )
            return latestFinishedSeasonAndRound to emptyList()
        }
        val season = now.year
        val endSeasonAndRound = latestFinishedSeasonAndRound ?: SeasonAndRound(season, END_ROUND)
        val result = mutableListOf<T>()
        for (seasonAndRound in startSeasonAndRound..endSeasonAndRound) {
            result.add(action(seasonAndRound, now))
        }
        return endSeasonAndRound to result
    }
}
