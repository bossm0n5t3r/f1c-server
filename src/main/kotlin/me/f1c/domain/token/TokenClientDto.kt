package me.f1c.domain.token

import me.f1c.security.TokenRole

data class TokenClientDto(
    val clientId: String,
    val clientSecret: String,
    val name: String,
    val role: TokenRole,
    val status: TokenClientStatus,
)
