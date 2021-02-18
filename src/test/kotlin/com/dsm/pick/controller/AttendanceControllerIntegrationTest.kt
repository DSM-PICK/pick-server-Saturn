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
                .queryParam("date", "2021-01-01")
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
        assertThat(response.locations).map<String> { it.name }.containsAll(listOf("테스트동아리"))
    }

    @Test
    fun `동아리 출석부 네비게이션 반환 - 일정을 찾을 수 없음 Activity Not Found Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/club/3")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2003-08-16")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ACTIVITY_NOT_FOUND")
    }

    @Test
    fun `동아리 출석부 네비게이션 반환 - 잘못된 층 요청 Invalid Request Body Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/club/10")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-01")
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
    fun `교실 출석부 네비게이션 반환 OK`() {
        val response = objectMapper.readValue<AttendanceNavigationResponse>(
            mock.perform(get("/attendance/navigation/self-study/3")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-02")
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
        assertThat(response.schedule).isEqualTo("self-study")
        assertThat(response.teacherName).isEqualTo("teacherName")
        assertThat(response.locations).map<String> { it.name }.containsAll(listOf("테스트교실"))
    }

    @Test
    fun `교실 출석부 네비게이션 반환 - 일정을 찾을 수 없음 Activity Not Found Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/self-study/3")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2003-08-16")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ACTIVITY_NOT_FOUND")
    }

    @Test
    fun `교실 출석부 네비게이션 반환 - 잘못된 층 요청 Invalid Request Body Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/navigation/self-study/10")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-01")
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
                .header("Authorization", "잘못된 토큰")
                .queryParam("date", "2021-01-01")
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
                .queryParam("date", "2021-01-01")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.name).isEqualTo("테스트동아리")
        assertThat(response.clubHead).isEqualTo("3417 이진혁")
        assertThat(response.attendances).map<String> { it.studentNumber }.containsAll(listOf("3417"))
    }

    @Test
    fun `동아리 출석 현황 반환 - 동아리를 찾을 수 없음 Club Not Found Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/club/3/10")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-01")
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
    fun `교실 출석 현황 반환 OK`() {
        val response = objectMapper.readValue<AttendanceResponse>(
            mock.perform(get("/attendance/student-state/self-study/3/0")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-02")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.name).isEqualTo("테스트교실")
        assertThat(response.clubHead).isNull()
        assertThat(response.attendances).map<String> { it.studentNumber }.containsAll(listOf("3417"))
    }

    @Test
    fun `교실 출석 현황 반환 - 교실을 찾을 수 없음 Classroom Not Found Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/self-study/3/10")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-02")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("CLASS_NOT_FOUND")
    }

    @Test
    fun `출석 현황 반환 - 존재하지 않는 일정 Non Exist Schedule Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(get("/attendance/student-state/after-school/3/0")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-01")
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
                .header("Authorization", "잘못된 토큰")
                .queryParam("date", "2021-01-01")
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
                period = Period.EIGHT,
                state = State.ATTENDANCE
            )
        )
        mock.perform(patch("/attendance/student-state")
            .header("Authorization", "this-is-test-token")
            .queryParam("date", "2021-01-01")
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
                period = Period.EIGHT,
                state = State.ATTENDANCE
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/student-state")
                .header("Authorization", "잘못된 토큰")
                .queryParam("date", "2021-01-01")
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
    fun `출석 상태 변경 - 출석부를 찾을 수 없음 Attendance Not Found Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            StudentStateRequest(
                number = "3417",
                period = Period.EIGHT,
                state = State.ATTENDANCE
            )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/student-state")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-10")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ATTENDANCE_NOT_FOUND")
    }

    @Test
    fun `메모 변경 OK`() {
        val requestBody = objectMapper.writeValueAsString(
            MemoRequest("메모메모")
        )
        mock.perform(patch("/attendance/memo/3417/8")
            .header("Authorization", "this-is-test-token")
            .queryParam("date", "2021-01-01")
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
            MemoRequest("메모메모")
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/memo/3417/8")
                .header("Authorization", "잘못된 토큰")
                .queryParam("date", "2021-01-01")
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
    fun `메모 변경 - 출석부를 찾을 수 없음 Attendance Not Found Exception`() {
        val requestBody = objectMapper.writeValueAsString(
            MemoRequest("메모메모")
        )
        val response = objectMapper.readValue<ExceptionResponse>(
            mock.perform(patch("/attendance/memo/3417/8")
                .header("Authorization", "this-is-test-token")
                .queryParam("date", "2021-01-10")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound)
                .andReturn()
                .response
                .contentAsString
        )

        assertThat(response.code).isEqualTo("ATTENDANCE_NOT_FOUND")
    }
}