package me.f1c.domain.driver

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DriverEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Drivers) {
    companion object : BaseLongEntityClass<DriverEntity>(Drivers)

    var season by Drivers.season
    var driverId by Drivers.driverId
    var permanentNumber by Drivers.permanentNumber
    var code by Drivers.code
    var url by Drivers.url
    var givenName by Drivers.givenName
    var familyName by Drivers.familyName
    var dateOfBirth by Drivers.dateOfBirth
    var nationality by Drivers.nationality
}
