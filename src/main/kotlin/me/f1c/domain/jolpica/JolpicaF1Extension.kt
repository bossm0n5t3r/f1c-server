package me.f1c.domain.jolpica

import kotlinx.datetime.LocalDateTime
import me.f1c.configuration.LOGGER
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime

private fun DateTime.toRaceDatetimeOrNull(): LocalDateTime? =
    try {
        "${this.date}T${this.time}".toKotlinLocalDateTime()
    } catch (e: Exception) {
        LOGGER.warn("Failed DateTime.toRaceDatetimeOrNull: {}, {}", this.date, this.time)
        null
    }

fun DateTime.toRaceDateTimeOrGivenTime(givenTime: LocalDateTime): LocalDateTime = this.toRaceDatetimeOrNull() ?: givenTime
