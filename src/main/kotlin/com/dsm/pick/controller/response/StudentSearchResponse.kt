package com.dsm.pick.controller.response

data class StudentSearchResponse(
    val twoFloorStudents: List<StudentInfo>,
    val threeFloorStudents: List<StudentInfo>,
    val fourFloorStudents: List<StudentInfo>,
) {

    data class StudentInfo(
        val studentNumber: String,
        val studentName: String,
    )
}