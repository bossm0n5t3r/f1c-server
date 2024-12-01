package me.f1c.domain.jolpica

class SeasonAndRoundIterator(
    start: SeasonAndRound,
    private val endInclusive: SeasonAndRound,
) : Iterator<SeasonAndRound> {
    private var init = start

    override fun hasNext(): Boolean = init <= endInclusive

    override fun next(): SeasonAndRound = init++
}
