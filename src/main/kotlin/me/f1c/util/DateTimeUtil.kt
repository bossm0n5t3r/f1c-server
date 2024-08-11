package me.f1c.util

import kotlinx.datetime.format.DateTimeComponents

object DateTimeUtil {
    fun String.toKotlinLocalDateTime() = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET.parse(this).toLocalDateTime()
}
