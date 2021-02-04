package com.dsm.pick.controller.response

class AttendanceNavigationResponse(
    val date: String,
    val dayOfWeek: String,
    val teacherName: String?,
    val schedule: String,
    val locations: List<LocationInformation>,
) {

    class LocationInformation(
        val location: String,
        val name: String,
        val done: String,
        val priority: String,
    )
}