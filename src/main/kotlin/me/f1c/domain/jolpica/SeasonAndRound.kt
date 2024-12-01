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
}
