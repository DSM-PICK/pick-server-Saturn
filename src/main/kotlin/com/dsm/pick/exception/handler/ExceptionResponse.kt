package com.dsm.pick.exception.handler

import org.springframework.http.HttpStatus

class ExceptionResponse(
    val code: String,
    val message: String,
    val status: HttpStatus,
)