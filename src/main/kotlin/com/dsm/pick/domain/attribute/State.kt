package com.dsm.pick.domain.attribute

enum class State(val value: String, val englishValue: String) {
    ATTENDANCE("출석", "attendance"),
    OUTING("외출", "outing"),
    FIELD_EXPERIENCE("현체", "field-experience"),
    HOME_COMING("귀가", "home-coming"),
    MOVE("이동", "move"),
    TRUANCY("무단", "truancy"),
    EMPLOYMENT("취업", "employment"),
    REPLENISHMENT("기초학력", "replenishment"),
    AFTER_SCHOOL("방과후", "after-school"),
}