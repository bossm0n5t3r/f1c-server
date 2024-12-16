package me.f1c.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class F1CExceptionHandlingAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseBody
    fun handleException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val f1CBadRequestException = F1CBadRequestException(e)
        return ResponseEntity(f1CBadRequestException.errorResponse, f1CBadRequestException.httpStatus)
    }

    @ExceptionHandler(F1CException::class)
    @ResponseBody
    fun handleException(e: F1CException): ResponseEntity<ErrorResponse> = ResponseEntity(e.errorResponse, e.httpStatus)
}
