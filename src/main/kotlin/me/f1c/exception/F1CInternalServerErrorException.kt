package me.f1c.exception

import org.springframework.http.HttpStatus

class F1CInternalServerErrorException(
    message: String? = null,
    vararg details: Pair<String, Any?>,
) : F1CException(HttpStatus.INTERNAL_SERVER_ERROR, message, *details) {
    constructor(e: Throwable) : this(e.message)
}
