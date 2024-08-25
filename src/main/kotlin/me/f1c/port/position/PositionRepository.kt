package me.f1c.port.position

import me.f1c.domain.position.PositionDto

interface PositionRepository {
    fun batchInsert(positionDtoList: List<PositionDto>): Int

    fun findAllBySessionKey(sessionKey: Int): List<PositionDto>
}
