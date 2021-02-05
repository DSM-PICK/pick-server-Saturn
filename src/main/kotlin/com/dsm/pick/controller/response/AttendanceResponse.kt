package com.dsm.pick.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

class AttendanceResponse(
    val attendances: List<StudentState>,
    @get:JsonProperty("head")
    val clubHead: String,
    @get:JsonProperty("name")
    val locationName: String,
) {

    class StudentState(
        @get:JsonProperty("gradeClassNumber")
        val studentNumber: String,
        @get:JsonProperty("name")
        val studentName: String,
        val state: State,
        val memo: Memo,
    ) {

        class State(
            val seven: String,
            val eight: String,
            val nine: String,
            val ten: String,
        )

        class Memo(
            val sevenMemo: String,
            val eightMemo: String,
            val nineMemo: String,
            val tenMemo: String,
        )
    }
}