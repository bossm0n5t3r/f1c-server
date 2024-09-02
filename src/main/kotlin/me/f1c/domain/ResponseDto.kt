package me.f1c.domain

data class ResponseDto<T>(
    val data: T,
)

fun <T : Any?> T.toResponseDto() = ResponseDto(this)
