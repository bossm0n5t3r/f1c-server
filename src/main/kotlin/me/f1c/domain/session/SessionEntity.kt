package me.f1c.domain.session

import me.f1c.domain.BaseLongEntity
import me.f1c.domain.BaseLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SessionEntity(id: EntityID<Long>) : BaseLongEntity(id, Sessions) {
    companion object : BaseLongEntityClass<SessionEntity>(Sessions)

    var sessionKey by Sessions.sessionKey
    var sessionName by Sessions.sessionName
    var dateStart by Sessions.dateStart
    var dateEnd by Sessions.dateEnd
    var gmtOffset by Sessions.gmtOffset
    var sessionType by Sessions.sessionType
    var meetingKey by Sessions.meetingKey
    var location by Sessions.location
    var countryCode by Sessions.countryCode
    var countryKey by Sessions.countryKey
    var countryName by Sessions.countryName
    var circuitKey by Sessions.circuitKey
    var circuitShortName by Sessions.circuitShortName
    var year by Sessions.year
}
