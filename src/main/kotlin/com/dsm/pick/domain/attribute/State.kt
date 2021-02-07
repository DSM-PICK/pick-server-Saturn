package com.dsm.pick.domain.attribute

enum class State(val value: String) {
    ATTENDANCE("출석"),
    OUTING("외출"),
    FIELD_EXPERIENCE("현체"),
    HOME_COMING("귀가"),
    MOVE("이동"),
    TRUANCY("무단"),
    EMPLOYMENT("취업"),
}