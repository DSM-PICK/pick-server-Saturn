package com.dsm.pick.controller.request

import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class StudentStateRequest(
    @NotNull(message = "허용하지 않는 형식 <NULL>")
    val period: Period,
    @NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val number: String,
    @NotNull(message = "허용하지 않는 형식 <NULL>")
    val state: String,
)