package me.f1c.exception

import org.springframework.http.HttpStatus

class F1CBadRequestException(
    message: String,
    vararg details: Pair<String, Any?>,
) : F1CException(HttpStatus.BAD_REQUEST, message, *details)
