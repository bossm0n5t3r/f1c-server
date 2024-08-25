package me.f1c.domain.driver

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class DriverEntity(
    id: EntityID<Long>,
) : BaseLongEntity(id, Drivers) {
    companion object : BaseLongEntityClass<DriverEntity>(Drivers)

    var driverNumber by Drivers.driverNumber
    var sessionKey by Drivers.sessionKey
    var meetingKey by Drivers.meetingKey
    var broadcastName by Drivers.broadcastName
    var countryCode by Drivers.countryCode
    var firstName by Drivers.firstName
    var lastName by Drivers.lastName
    var fullName by Drivers.fullName
    var headshotUrl by Drivers.headshotUrl
    var teamColour by Drivers.teamColour
    var teamName by Drivers.teamName
    var nameAcronym by Drivers.nameAcronym
}
