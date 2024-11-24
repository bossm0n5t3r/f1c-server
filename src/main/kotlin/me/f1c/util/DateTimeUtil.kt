package me.f1c.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents

object DateTimeUtil {
    fun String.toKotlinLocalDateTime() =
        DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
            .parse(this)
            .toLocalDateTime()

    private const val SERVER_ZONE_ID = "Asia/Seoul"
    val SERVER_TIME_ZONE = TimeZone.of(SERVER_ZONE_ID)
}
