package com.dsm.pick.controller.request

data class StudentMemoModificationRequest(
    val memo: String,
    val numbers: List<String>,
    val periods: List<Int>,
)