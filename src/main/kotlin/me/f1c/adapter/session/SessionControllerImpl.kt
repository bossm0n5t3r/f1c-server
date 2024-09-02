package me.f1c.adapter.session

import me.f1c.domain.ResponseDto
import me.f1c.domain.session.SessionDto
import me.f1c.domain.session.SessionService
import me.f1c.domain.toResponseDto
import me.f1c.port.session.SessionController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sessions")
class SessionControllerImpl(
    private val sessionService: SessionService,
) : SessionController {
    @PutMapping("/up-to-date")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun upToDate(): ResponseDto<Int> = sessionService.upToDate().toResponseDto()

    @GetMapping
    override fun findAll(): ResponseDto<List<SessionDto>> = sessionService.findAll().toResponseDto()

    @GetMapping("/{sessionKey}")
    override fun findBySessionKey(
        @PathVariable sessionKey: Int,
    ): ResponseDto<SessionDto?> = sessionService.findBySessionKey(sessionKey).toResponseDto()

    @GetMapping("/session-keys")
    override fun findAllSessionKeys(): ResponseDto<List<Int>> = sessionService.findAllSessionKeys().toResponseDto()
}
