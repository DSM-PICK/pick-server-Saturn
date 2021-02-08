package com.dsm.pick.controller.request

import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class StudentStateRequest(
    @NotNull
    val period: Period,
    @NotBlank
    val number: String,
    @NotNull
    val state: State,
)