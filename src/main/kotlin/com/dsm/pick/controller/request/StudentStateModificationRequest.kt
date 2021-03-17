package com.dsm.pick.controller.request

data class StudentStateModificationRequest(
    val state: String,
    val numbers: List<String>,
    val periods: List<Int>,
)