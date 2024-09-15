package me.f1c.domain.admin

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.driver.DriverService
import me.f1c.domain.position.PositionService
import me.f1c.domain.session.SessionService
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val sessionService: SessionService,
    private val driverService: DriverService,
    private val positionService: PositionService,
) {
    fun upToDate() =
        try {
            sessionService.upToDate()

            // Get All Session Keys
            val allSessionKeys = sessionService.findAll().map { it.sessionKey }.distinct()
            val updatedSessionKeyFromDriver = mutableListOf<Int>()
            val updatedSessionKeyFromPosition = mutableListOf<Int>()
            for (sessionKey in allSessionKeys) {
                if (driverService.findAll(sessionKey).isEmpty()) {
                    driverService.upToDate(sessionKey)
                    updatedSessionKeyFromDriver.add(sessionKey)
                }
                if (positionService.findAllBySessionKey(sessionKey).isEmpty()) {
                    positionService.upToDate(sessionKey)
                    updatedSessionKeyFromPosition.add(sessionKey)
                }
            }
            LOGGER.info("${LogResult.SUCCEEDED} upToDate: {}, {}", updatedSessionKeyFromDriver.size, updatedSessionKeyFromPosition.size)
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} upToDate: {}, ", e.message, e)
            throw e
        }
}
