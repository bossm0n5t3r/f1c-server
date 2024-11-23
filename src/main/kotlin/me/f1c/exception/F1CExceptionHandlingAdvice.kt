package me.f1c.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class F1CExceptionHandlingAdvice {
    @ExceptionHandler(F1CException::class)
    @ResponseBody
    fun handleException(e: F1CException): ResponseEntity<ErrorResponse> = ResponseEntity(e.errorResponse, e.httpStatus)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity(F1CBadRequestException(e).errorResponse, F1CBadRequestException(e).httpStatus)
}
