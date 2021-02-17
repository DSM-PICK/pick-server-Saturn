package com.dsm.pick.controller

import com.dsm.pick.controller.request.LoginRequest
import com.dsm.pick.controller.response.LoginResponse
import com.dsm.pick.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthController::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class AuthControllerTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {
    @MockBean
    private lateinit var authService: AuthService

    @Test
    fun `로그인 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                id = "teacherId",
                password = "teacherPassword",
            )
        )

//        val response = objectMapper.readValue<LoginResponse>(
        val a =    mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
//        )

        println("a : $a")
        println("a.response : ${a.response}")
    }
}