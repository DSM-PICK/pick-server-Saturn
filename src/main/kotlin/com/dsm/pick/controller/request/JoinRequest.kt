package com.dsm.pick.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JoinRequest(
    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9-]{4,16}$", message = "정규표현식 = ^[a-zA-Z0-9|-]{4,16}$")
    val id: String,

    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{4,20}$", message = "정규표현식 = ^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
    val password: String,

    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9|!@#$%^&*]{4,20}$", message = "정규표현식 = ^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
    val confirmPassword: String,

    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
    val name: String,

    @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
    val managedClassroomName: String,
)