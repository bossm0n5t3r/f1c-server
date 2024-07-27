package me.f1c.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline val <reified T> T.LOGGER: Logger
    get() = LoggerFactory.getLogger(T::class.java)

enum class LogResult {
    SUCCEEDED,
    FAILED,
}
