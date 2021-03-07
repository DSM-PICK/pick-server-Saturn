package com.dsm.pick.service

enum class Token(val expirationTime: Int) {
    ACCESS(expirationTime = 1000 * 60 * 60 * 6),
    REFRESH(expirationTime = 1000 * 60 * 60 * 24),
}