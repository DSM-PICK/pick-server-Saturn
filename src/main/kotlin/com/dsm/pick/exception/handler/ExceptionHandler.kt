package com.dsm.pick.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentNotValidException) =
        ExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "클라이언트의 요청이 잘못되었습니다. [${e.bindingResult.allErrors.first().defaultMessage}]",
        )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun notValidateExceptionHandler(e: MethodArgumentTypeMismatchException) =
        ExceptionResponse(
            code = "INVALID_REQUEST_BODY",
            message = "${e.mostSpecificCause.message}",
        )

    @ExceptionHandler(CommonException::class)
    fun commonExceptionHandler(e: CommonException) =
        ResponseEntity(
            ExceptionResponse(
                code = e.code,
                message = e.message ?: "큰 문제는 아닌데 이거 나오면 안 되긴 함",
            ),
            e.status,
        )

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun runtimeExceptionHandler(e: RuntimeException): ExceptionResponse {
        e.printStackTrace()
        return ExceptionResponse(
            code = "INTERVAL_SERVER_ERROR",
            message = "이거 서버 에러임 이진혁한테 따지러 가삼",
        )
    }
}