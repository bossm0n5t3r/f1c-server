package me.f1c.adapter.driver

import me.f1c.domain.driver.DriverDto
import me.f1c.domain.driver.DriverEntity
import me.f1c.domain.driver.Drivers
import me.f1c.domain.driver.toDto
import me.f1c.port.driver.DriverRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class DriverRepositoryImpl(
    private val database: Database,
) : DriverRepository {
    override fun findAllBySeason(season: Int): List<DriverDto> =
        transaction(database) {
            Drivers
                .selectAll()
                .where { Drivers.season eq season }
                .run { DriverEntity.wrapRows(this) }
                .map { it.toDto() }
        }

    override fun batchInsert(driverDtoList: List<DriverDto>): Int =
        transaction(database) {
            Drivers.batchInsert(driverDtoList) {
                this[Drivers.season] = it.season
                this[Drivers.driverId] = it.driverId
                this[Drivers.permanentNumber] = it.permanentNumber
                this[Drivers.code] = it.code
                this[Drivers.url] = it.url
                this[Drivers.givenName] = it.givenName
                this[Drivers.familyName] = it.familyName
                this[Drivers.dateOfBirth] = it.dateOfBirth
                this[Drivers.nationality] = it.nationality
            }
        }.size
}
