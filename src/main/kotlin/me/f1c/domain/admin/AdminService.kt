package me.f1c.domain.admin

import me.f1c.configuration.LOGGER
import me.f1c.configuration.LogResult
import me.f1c.domain.driver.DriverService
import me.f1c.domain.position.PositionService
import me.f1c.domain.session.SessionService
import me.f1c.port.session.SessionRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val sessionService: SessionService,
    private val sessionRepository: SessionRepository,
    private val driverService: DriverService,
    private val positionService: PositionService,
) {
    fun upToDate() =
        try {
            sessionService.upToDate()
            val latestSession = requireNotNull(sessionRepository.findLatestSession()) { "Not found latest session" }
            val latestSessionKey = latestSession.sessionKey

            driverService.upToDate(latestSessionKey)
            positionService.upToDate(latestSessionKey)

            LOGGER.info("${LogResult.SUCCEEDED} upToDate")
            latestSessionKey
        } catch (e: Exception) {
            LOGGER.error("${LogResult.FAILED} upToDate: {}, ", e.message, e)
            throw e
        }
}
