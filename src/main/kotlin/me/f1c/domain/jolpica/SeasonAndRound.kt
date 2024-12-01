package me.f1c.domain.jolpica

data class SeasonAndRound(
    val season: Int,
    val round: Int,
) : Comparable<SeasonAndRound> {
    override fun compareTo(other: SeasonAndRound): Int {
        if (this.season != other.season) {
            return this.season.compareTo(other.season)
        }
        return this.round.compareTo(other.round)
    }

    operator fun rangeTo(that: SeasonAndRound) = SeasonAndRoundRange(this, that)

    operator fun inc(): SeasonAndRound {
        val nextRound = this.round + 1
        return if (nextRound > 24) {
            SeasonAndRound(this.season + 1, 1)
        } else {
            SeasonAndRound(this.season, nextRound)
        }
    }
}
