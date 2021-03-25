package com.dsm.pick.controller.response

data class StudentSearchResponse(
    val students: List<StudentInfo>
) {

    data class StudentInfo(
        val studentNumber: String,
        val studentName: String,
    )
}