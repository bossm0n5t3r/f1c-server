package me.f1c.port.circuit

import me.f1c.domain.circuit.CircuitDto

interface CircuitRepository {
    fun batchInsert(circuitDtoList: List<CircuitDto>): Int

    fun findAllBySeason(season: Int): List<CircuitDto>

    fun findBySeasonAndCircuitIdOrNull(
        season: Int,
        circuitId: String,
    ): CircuitDto?
}
