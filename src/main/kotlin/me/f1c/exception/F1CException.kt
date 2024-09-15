package me.f1c.exception

import org.springframework.http.HttpStatus

abstract class F1CException(
    val httpStatus: HttpStatus,
    final override val message: String,
    vararg details: Pair<String, Any?>,
) : Exception(message) {
    val errorResponse =
        ErrorResponse(
            errorMessage = this.message,
            details = details.takeIf { it.isNotEmpty() }?.toMap(),
        )
}
