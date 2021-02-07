package com.dsm.pick.controller.request

import com.dsm.pick.domain.converter.attribute.Period
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class StudentStateRequest(
    @NotNull
    val period: Period,
    @NotBlank
    val number: String,
    @NotNull
    val state: String,
)