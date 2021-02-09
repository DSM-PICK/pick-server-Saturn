package com.dsm.pick.controller.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val teacherName: String,
    val managedClassroom: ManagedClassroom?,
) {

    data class ManagedClassroom(
        val name: String,
        val floor: Int,
        val priority: Int,
    )
}