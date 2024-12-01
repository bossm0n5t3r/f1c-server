package me.f1c.domain.jolpica

class SeasonAndRoundRange(
    override val start: SeasonAndRound,
    override val endInclusive: SeasonAndRound,
) : ClosedRange<SeasonAndRound>,
    Iterable<SeasonAndRound> {
    override fun iterator(): Iterator<SeasonAndRound> = SeasonAndRoundIterator(start, endInclusive)
}
