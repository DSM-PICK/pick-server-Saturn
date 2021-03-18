package com.dsm.pick.controller.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val teacherName: String,
    val managedClassroom: ManagedClassroom?,
    val managedClub: List<ManagedClub>,
) {

    data class ManagedClassroom(
        val name: String,
        val floor: Int,
        val priority: Int,
    )

    data class ManagedClub(
        val name: String,
        val location: String,
        val floor: Int,
        val priority: Int,
    )
}