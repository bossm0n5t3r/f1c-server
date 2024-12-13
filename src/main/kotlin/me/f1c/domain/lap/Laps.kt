package me.f1c.domain.lap

import com.fasterxml.jackson.module.kotlin.readValue
import me.f1c.domain.BaseLongIdTable
import me.f1c.util.ObjectMapperUtil
import org.jetbrains.exposed.sql.json.json
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Laps : BaseLongIdTable("laps") {
    val season = integer("season")
    val round = integer("round")
    val raceName = varchar("race_name", 200)
    val circuitId = varchar("circuit_id", 100)
    val raceDatetime = datetime("race_datetime")
    val lapNumber = integer("lap_number")
    val positions =
        json(
            "positions",
            { ObjectMapperUtil.objectMapper.writeValueAsString(it) },
            { ObjectMapperUtil.objectMapper.readValue<List<PositionDto>>(it) },
        )
}
