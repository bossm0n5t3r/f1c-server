package me.f1c.port.constructor

import me.f1c.domain.constructor.ConstructorDto

interface ConstructorRepository {
    fun batchInsert(constructorDtoList: List<ConstructorDto>): Int

    fun findAllBySeason(season: Int): List<ConstructorDto>
}
