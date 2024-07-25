package me.f1c.adapter

import me.f1c.domain.SessionDto
import me.f1c.domain.SessionService
import me.f1c.port.SessionController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sessions")
class SessionControllerImpl(
    private val sessionService: SessionService,
) : SessionController {
    @GetMapping
    override fun findAll(): List<SessionDto> = sessionService.findAll()

    @GetMapping("/{sessionKey}")
    override fun findBySessionKey(
        @PathVariable sessionKey: Int,
    ): SessionDto? = sessionService.findBySessionKey(sessionKey)

    @GetMapping("/session-keys")
    override fun findAllSessionKeys(): List<Int> = sessionService.findAllSessionKeys()
}
