package com.dsm.pick.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class LoginRequest(
    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val id: String,
    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @get:JsonProperty("pw")
    val password: String,
)