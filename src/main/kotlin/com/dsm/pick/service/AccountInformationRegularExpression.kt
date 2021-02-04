package com.dsm.pick.service

enum class AccountInformationRegularExpression(val regex: String) {
    ID("^[a-zA-Z0-9|-]{4,16}$"),
    PASSWORD("^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$"),
    NAME("^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$"),
    OFFICE("^[a-zA-Z0-9ㄱ-ㅎ가-힣\\s]{1,40}$"),
}