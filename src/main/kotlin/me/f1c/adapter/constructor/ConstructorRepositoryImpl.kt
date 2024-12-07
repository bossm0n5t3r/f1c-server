package me.f1c.adapter.constructor

import me.f1c.domain.constructor.ConstructorDto
import me.f1c.domain.constructor.ConstructorEntity
import me.f1c.domain.constructor.Constructors
import me.f1c.domain.constructor.toDto
import me.f1c.port.constructor.ConstructorRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class ConstructorRepositoryImpl(
    private val database: Database,
) : ConstructorRepository {
    override fun batchInsert(constructorDtoList: List<ConstructorDto>): Int =
        transaction(database) {
            Constructors.batchInsert(constructorDtoList) {
                this[Constructors.season] = it.season
                this[Constructors.constructorId] = it.constructorId
                this[Constructors.url] = it.url
                this[Constructors.name] = it.name
                this[Constructors.nationality] = it.nationality
            }
        }.size

    override fun findAllBySeason(season: Int): List<ConstructorDto> =
        transaction(database) {
            Constructors
                .selectAll()
                .where { Constructors.season eq season }
                .run { ConstructorEntity.wrapRows(this) }
                .map { it.toDto() }
        }
}
