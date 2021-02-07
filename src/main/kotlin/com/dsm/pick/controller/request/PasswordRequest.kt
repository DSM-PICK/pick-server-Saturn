package com.dsm.pick.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class PasswordRequest(
    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @Pattern(regexp = "^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
    val confirmNewPassword: String,

    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @Pattern(regexp = "^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
    val newPassword: String,
)