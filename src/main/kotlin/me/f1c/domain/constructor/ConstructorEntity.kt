package me.f1c.domain.constructor

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ConstructorEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Constructors) {
    companion object : BaseLongEntityClass<ConstructorEntity>(Constructors)

    var season by Constructors.season
    var constructorId by Constructors.constructorId
    var url by Constructors.url
    var name by Constructors.name
    var nationality by Constructors.nationality
}
