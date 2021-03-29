package com.dsm.pick.controller.request

data class StudentModificationRequest(
    val state: String,
    val memo: String,
    val numbers: List<String>,
    val periods: List<Int>,
)