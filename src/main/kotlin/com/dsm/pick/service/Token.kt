package com.dsm.pick.service

enum class Token(val expirationTime: Int) {
    ACCESS(expirationTime = 1000 * 60 * 30),
    REFRESH(expirationTime = 1000 * 60 * 60 * 4),
}