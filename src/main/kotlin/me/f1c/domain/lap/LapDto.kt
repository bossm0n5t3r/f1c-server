package me.f1c.domain.lap

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
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
    @JsonProperty("duration_sector_1")
    val durationSector1: BigDecimal?,
    @JsonProperty("duration_sector_2")
    val durationSector2: BigDecimal?,
    @JsonProperty("duration_sector_3")
    val durationSector3: BigDecimal?,
    @JsonProperty("segments_sector_1")
    val segmentsSector1: List<Int>,
    @JsonProperty("segments_sector_2")
    val segmentsSector2: List<Int>,
    @JsonProperty("segments_sector_3")
    val segmentsSector3: List<Int>,
    val lapNumber: Int,
)
