package me.f1c.adapter.driver

import me.f1c.domain.driver.DriverDto
import me.f1c.domain.driver.DriverEntity
import me.f1c.domain.driver.Drivers
import me.f1c.domain.driver.toDto
import me.f1c.port.driver.DriverRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class DriverRepositoryImpl(
    private val database: Database,
) : DriverRepository {
    override fun findAllBySessionKey(sessionKey: Int): List<DriverDto> =
        transaction(database) {
            Drivers
                .selectAll()
                .where { Drivers.sessionKey eq sessionKey }
                .run { DriverEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    override fun batchInsert(driverDtoList: List<DriverDto>): Int =
        transaction(database) {
            Drivers.batchInsert(driverDtoList) {
                this[Drivers.driverNumber] = it.driverNumber
                this[Drivers.sessionKey] = it.sessionKey
                this[Drivers.meetingKey] = it.meetingKey
                this[Drivers.broadcastName] = it.broadcastName
                this[Drivers.countryCode] = it.countryCode
                this[Drivers.firstName] = it.firstName
                this[Drivers.lastName] = it.lastName
                this[Drivers.fullName] = it.fullName
                this[Drivers.headshotUrl] = it.headshotUrl
                this[Drivers.teamColour] = it.teamColour
                this[Drivers.teamName] = it.teamName
                this[Drivers.nameAcronym] = it.nameAcronym
            }
        }.size

    override fun findAllByDriverNumberOrderBySessionKey(driverNumber: Int): List<DriverDto> =
        transaction(database) {
            Drivers
                .selectAll()
                .where { Drivers.driverNumber eq driverNumber }
                .orderBy(Drivers.sessionKey, SortOrder.ASC)
                .run { DriverEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
