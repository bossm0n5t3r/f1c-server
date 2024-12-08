package me.f1c.domain.result

import me.f1c.domain.circuit.CircuitDto

data class RankingDto(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuit: CircuitDto,
    val raceDatetime: String,
    val drivers: List<RankedDriverDto>,
)
