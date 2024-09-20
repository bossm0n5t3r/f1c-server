package me.f1c.exception

import org.springframework.http.HttpStatus

abstract class F1CException(
    val httpStatus: HttpStatus,
    message: String? = null,
    vararg details: Pair<String, Any?>,
) : Exception(message) {
    val errorResponse =
        ErrorResponse(
            errorMessage = this.message,
            details = details.takeIf { it.isNotEmpty() }?.toMap(),
        )
}
