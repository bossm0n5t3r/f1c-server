package me.f1c.domain.jolpica

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.f1c.util.Constants.END_ROUND
import me.f1c.util.Constants.START_ROUND
import kotlin.test.Test
import kotlin.test.assertEquals

class SeasonAndRoundTest {
    @Test
    fun iteratorTest() {
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val season = now.year

        var count = 0
        val startSeasonAndRound = SeasonAndRound(season, START_ROUND)
        val endSeasonAndRound = SeasonAndRound(season + 1, END_ROUND)

        for (seasonAndRound in startSeasonAndRound..endSeasonAndRound) count++

        assertEquals(END_ROUND * 2, count)
    }
}
