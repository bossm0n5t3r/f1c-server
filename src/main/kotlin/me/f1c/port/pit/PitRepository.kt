package me.f1c.port.pit

import me.f1c.domain.pit.PitDto

interface PitRepository {
    fun batchInsert(pitDtoList: List<PitDto>): Int

    fun findAllBySessionKey(sessionKey: Int): List<PitDto>
}
