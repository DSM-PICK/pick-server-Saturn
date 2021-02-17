package com.dsm.pick.service.test

import com.dsm.pick.service.JwtService
import com.dsm.pick.service.Token
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class TestJwtService : JwtService("token-love") {

    override fun createToken(teacherId: String, tokenType: Token) = "this-is-test-token"

    override fun getTeacherId(token: String): String {
        isValid(token)
        return "teacherId"
    }

    override fun isValid(token: String) = token == "this-is-test-token"
}