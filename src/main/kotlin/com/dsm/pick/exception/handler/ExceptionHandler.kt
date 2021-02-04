package com.dsm.pick.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [RuntimeException::class])
    fun runtimeExceptionHandler(e: RuntimeException) =
        ExceptionResponse(
            code = "INTERVAL_SERVER_ERROR",
            message = "이거 서버 에러임 이진혁한테 따지러 가삼",
            status = HttpStatus.INTERNAL_SERVER_ERROR,
        )

    @ExceptionHandler(value = [CommonException::class])
    fun commonExceptionHandler(e: CommonException) =
        ExceptionResponse(
            code = e.code,
            message = e.message ?: "큰 문제는 아닌데 이거 나오면 안 되긴 함",
            status = e.status,
        )
}