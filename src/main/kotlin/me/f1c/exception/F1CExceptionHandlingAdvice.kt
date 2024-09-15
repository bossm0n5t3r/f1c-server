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
}
