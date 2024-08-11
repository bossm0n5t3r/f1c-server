package me.f1c.util

import kotlinx.datetime.LocalDateTime
import me.f1c.util.DateTimeUtil.toKotlinLocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class DateTimeUtilTest {
    @Test
    fun toKotlinLocalDateTimeTest() {
        val parsedKotlinLocalDateTime =
            assertDoesNotThrow {
                "2024-07-28T13:05:49.725000+00:00".toKotlinLocalDateTime()
            }

        assertThat(parsedKotlinLocalDateTime)
            .isEqualTo(LocalDateTime(2024, 7, 28, 13, 5, 49, 725000000))
    }
}
