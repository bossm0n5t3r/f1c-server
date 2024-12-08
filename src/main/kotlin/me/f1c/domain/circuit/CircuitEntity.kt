package me.f1c.domain.circuit

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CircuitEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Circuits) {
    companion object : BaseLongEntityClass<CircuitEntity>(Circuits)

    var season by Circuits.season
    var circuitId by Circuits.circuitId
    var url by Circuits.url
    var circuitName by Circuits.circuitName
    var latitude by Circuits.latitude
    var longitude by Circuits.longitude
    var country by Circuits.country
    var locality by Circuits.locality
}
