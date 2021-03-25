package com.dsm.pick.controller.response

data class MemoKindResponse(
    val twoFloorMemoKind: List<String>,
    val threeFloorMemoKind: List<String>,
    val fourFloorMemoKind: List<String>,
)