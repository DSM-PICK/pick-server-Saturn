package com.dsm.pick.controller.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class StudentStateRequest(
    @NotNull(message = "허용하지 않는 형식 <NULL>")
    @Min(value = 1, message = "1 미만 숫자는 허용하지 않습니다.")
    @Max(value = 10, message = "10 초과 숫자는 허용하지 않습니다.")
    val period: Int,
    @NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val number: String,
    @NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val state: String,
)