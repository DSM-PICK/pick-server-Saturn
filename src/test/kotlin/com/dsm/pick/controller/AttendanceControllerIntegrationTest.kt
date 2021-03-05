package com.dsm.pick.controller

import com.dsm.pick.controller.request.MemoRequest
import com.dsm.pick.controller.request.StudentStateRequest
import com.dsm.pick.controller.response.AttendanceNavigationResponse
import com.dsm.pick.controller.response.AttendanceResponse
import com.dsm.pick.domain.attribute.Period
import com.dsm.pick.domain.attribute.State
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Suppress("DEPRECATION")
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AttendanceControllerIntegrationTest(
    private val mock: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun `동아리 출석부 네비게이션 반환 OK`() {
        val response = objectMapper.readValue<AttendanceNavigationResponse>(
            mock.perform(get("/attendance/navigation/club/3")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.date).isNotBlank
        assertThat(response.dayOfWeek).isNotBlank
        assertThat(response.schedule).isEqualTo("club")
        assertThat(response.teacherName).isEqualTo("teacherName")
        assertThat(response.locations).map<String> { it.name }.containsAll(listOf("testClub"))
    }

    @Test
    fun `동아리 출석부 네비게이션 반환 - 잘못된 층 요청 Invalid Request Body Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/club/10")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("INVALID_REQUEST_BODY")
    }

    @Test
    fun `출석부 네비게이션 반환 - 잘못된 토큰 Invalid Token Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/club/3")
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
    fun `동아리 출석 현황 반환 OK`() {
        val response = objectMapper.readValue<AttendanceResponse>(
            mock.perform(get("/attendance/student-state/club/3/0")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.name).isEqualTo("testClub")
        assertThat(response.clubHead).isEqualTo("3417 Jin")
        assertThat(response.attendances).map<String> { it.studentNumber }.containsAll(listOf("3417"))
    }

    @Test
    fun `동아리 출석 현황 반환 - 동아리를 찾을 수 없음 Club Not Found Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/club/3/10")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("CLUB_NOT_FOUND")
    }

    @Test
    fun `출석 현황 반환 - 존재하지 않는 일정 Non Exist Schedule Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/after-school/3/0")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("NON_EXIST_SCHEDULE")
    }

    @Test
    fun `출석 현황 반환 - 잘못된 토큰 Invalid Token`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/club/3/0")
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
    fun `출석 상태 변경 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            StudentStateRequest(
                number = "3417",
                period = 8,
                state = "출석"
            )
        )
        mock.perform(patch("/attendance/student-state")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString
    }

    @Test
    fun `출석 상태 변경 - 잘못된 토큰 Invalid Token`() {
        val requestBody = objectMapper.writeValueAsString(
            StudentStateRequest(
                number = "3417",
                period = 8,
                state = "출석"
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/student-state")
                .header("Authorization", "invalid token")
                .content(requestBody)
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
    fun `메모 변경 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            MemoRequest("memo")
        )
        mock.perform(patch("/attendance/memo/3417/8")
            .header("Authorization", "this-is-test-token")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .characterEncoding("UTF-8"))
            .andExpect(status().isOk)
            .andReturn()
            .response
            .contentAsString
    }

    @Test
    fun `메모 변경 - 잘못된 토큰 Invalid Token`() {
        val requestBody = objectMapper.writeValueAsString(
            MemoRequest("memo")
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/memo/3417/8")
                .header("Authorization", "invalid token")
                .content(requestBody)
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
}