package com.dsm.pick.controller.response

class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val teacherName: String,
    val managedClassroom: String?,
)