package com.dsm.pick.controller

import com.dsm.pick.controller.request.AuthenticationNumberRequest
import com.dsm.pick.controller.request.JoinRequest
import com.dsm.pick.controller.request.LoginRequest
import com.dsm.pick.controller.request.PasswordRequest
import com.dsm.pick.controller.response.AccessTokenResponse
import com.dsm.pick.controller.response.LoginResponse
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.exception.handler.ExceptionResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AuthControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `로그인 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                id = "teacherId",
                password = "teacherPassword",
            )
        )
        val response = objectMapper.readValue<LoginResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.accessToken).isNotBlank
        assertThat(response.refreshToken).isNotBlank
        assertThat(response.teacherName).isEqualTo("teacherName")
        assertThat(response.managedClassroom?.name).isEqualTo("testClassroom")
        assertThat(response.managedClassroom?.floor).isEqualTo(Floor.THREE.value)
        assertThat(response.managedClassroom?.priority).isEqualTo(0)
    }

    @Test
    fun `로그인 - 아이디와 일치하는 계정 없음 Account Information Mismatch Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                id = "void",
                password = "teacherPassword",
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ACCOUNT_INFORMATION_MISMATCH")
    }

    @Test
    fun `로그인 - 비밀번호가 일치하지 않음 Account Information Mismatch Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            LoginRequest(
                id = "teacherId",
                password = "void",
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ACCOUNT_INFORMATION_MISMATCH")
    }

    @Test
    fun `토큰 유효성 검사 OK`() {
        mock.perform(post("/auth/token")
            .header("Authorization", "this-is-test-token")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `토큰 유효성 검사 - 토큰이 잘못됨 Invalid Token Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/token")
                .header("Authorization", "invalid token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isUnauthorized)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `토큰 재발급 OK`() {
        val response = objectMapper.readValue<AccessTokenResponse>(
            mock.perform(get("/auth/access-token")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.accessToken).isEqualTo("this-is-test-token")
    }

    @Test
    fun `토큰 재발급 - 토큰이 잘못됨 Invalid Token Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/auth/access-token")
                .header("Authorization", "invalid token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isUnauthorized)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `비밀번호 변경 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            PasswordRequest(
                teacherId = "teacherId",
                newPassword = "newPassword",
                authenticationNumber = "kotlin-good",
            )
        )
        mock.perform(patch("/auth/password")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `인증번호 확인 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            AuthenticationNumberRequest("kotlin-good")
        )
        mock.perform(post("/auth/authentication-number")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `인증번호 확인 - 인증번호가 다름 Authentication Number Mismatch Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            AuthenticationNumberRequest("invalid authentication number")
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/authentication-number")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("AUTHENTICATION_NUMBER_MISMATCH")
    }

    @Test
    fun `회원가입 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            JoinRequest(
                id = "newTeacherId",
                password = "teacherPassword",
                confirmPassword = "teacherPassword",
                name = "teacherName",
                managedClassroomName = "testClassroom",
            )
        )
        mock.perform(post("/auth/join")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
    }

    @Test
    fun `회원가입 - 새비밀번호와 비밀번호 재입력이 서로 다름 Account Information Mismatch Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            JoinRequest(
                id = "newTeacherId",
                password = "teacherPassword",
                confirmPassword = "password",
                name = "teacherName",
                managedClassroomName = "savedClassroom",
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ACCOUNT_INFORMATION_MISMATCH")
    }

    @Test
    fun `회원가입 - 이미 존재하는 계정 Already Exist Account Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            JoinRequest(
                id = "teacherId",
                password = "teacherPassword",
                confirmPassword = "teacherPassword",
                name = "teacherName",
                managedClassroomName = "savedClassroom",
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(post("/auth/join")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ALREADY_EXIST_ACCOUNT")
    }
}