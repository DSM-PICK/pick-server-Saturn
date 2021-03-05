package com.dsm.pick.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class StudentStateRequest(
    @NotNull(message = "허용하지 않는 형식 <NULL>")
    val period: Int,
    @NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val number: String,
    @NotNull(message = "허용하지 않는 형식 <NULL>")
    val state: String,
)