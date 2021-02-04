package com.dsm.pick.controller.request

import javax.validation.constraints.NotBlank

class TeacherRequest(
    @get:NotBlank
    val id: String,
    @get:NotBlank
    val pw: String,
)