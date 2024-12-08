package me.f1c.adapter.circuit

import me.f1c.domain.circuit.CircuitDto
import me.f1c.domain.circuit.CircuitEntity
import me.f1c.domain.circuit.Circuits
import me.f1c.domain.circuit.toDto
import me.f1c.port.circuit.CircuitRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class CircuitRepositoryImpl(
    private val database: Database,
) : CircuitRepository {
    override fun batchInsert(circuitDtoList: List<CircuitDto>): Int =
        transaction(database) {
            Circuits.batchInsert(circuitDtoList) {
                this[Circuits.season] = it.season
                this[Circuits.circuitId] = it.circuitId
                this[Circuits.url] = it.url
                this[Circuits.circuitName] = it.circuitName
                this[Circuits.latitude] = it.latitude
                this[Circuits.longitude] = it.longitude
                this[Circuits.country] = it.country
                this[Circuits.locality] = it.locality
            }
        }.size

    override fun findAllBySeason(season: Int): List<CircuitDto> =
        transaction(database) {
            Circuits
                .selectAll()
                .where { Circuits.season eq season }
                .run { CircuitEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
