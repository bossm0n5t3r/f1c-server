package me.f1c.domain.lap

import java.math.BigDecimal

data class LapDto(
    val meetingKey: Int,
    val sessionKey: Int,
    val driverNumber: Int,
    val i1Speed: Int?,
    val i2Speed: Int?,
    val stSpeed: Int?,
    val dateStart: String?,
    val lapDuration: BigDecimal?,
    val isPitOutLap: Boolean,
    val durationSector1: BigDecimal?,
    val durationSector2: BigDecimal?,
    val durationSector3: BigDecimal?,
    val segmentsSector1: List<Int?>,
    val segmentsSector2: List<Int?>,
    val segmentsSector3: List<Int?>,
    val lapNumber: Int,
)
