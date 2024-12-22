package me.f1c.domain.token

fun TokenClientEntity.toDto() =
    TokenClientDto(
        clientId = clientId,
        clientSecret = clientSecret,
        name = name,
        role = role,
        status = status,
    )
