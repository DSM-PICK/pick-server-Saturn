package com.dsm.pick.controller.request

import javax.validation.constraints.NotBlank

class AuthenticationNumberRequest(
    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val authenticationNumber: String,
)