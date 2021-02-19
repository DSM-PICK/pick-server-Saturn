package com.dsm.pick.service

import com.dsm.pick.controller.response.LoginResponse
import com.dsm.pick.controller.response.LoginResponse.ManagedClassroom
import com.dsm.pick.domain.Classroom
import com.dsm.pick.domain.Teacher
import com.dsm.pick.domain.attribute.Floor
import com.dsm.pick.exception.AccountInformationMismatchException
import com.dsm.pick.exception.AlreadyExistAccountException
import com.dsm.pick.exception.AuthenticationNumberMismatchException
import com.dsm.pick.exception.InvalidTokenException
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AuthServiceTest {

    @Test
    fun `로그인 OK`() {
        val actual = authService.login(
            teacherId = "teacherId",
            teacherPassword = "teacherPassword",
        )

        val expected = LoginResponse(
            accessToken = "this-is-test-token",
            refreshToken = "this-is-test-token",
            teacherName = "teacherName",
            managedClassroom = ManagedClassroom(
                name = "testClassroom",
                floor = 3,
                priority = 0,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `로그인 존재하지 않는 계정 Account Information Mismatch Exception`() {
        assertThrows<AccountInformationMismatchException> {
            authService.login(
                teacherId = "void",
                teacherPassword = "teacherPassword",
            )
        }
    }

    @Test
    fun `토큰 검사 OK`() {
        authService.validateToken(token = "this-is-test-token")
    }

    @Test
    fun `토큰 검사 중 토큰 유효성 검사 실패 Invalid Token Exception`() {
        assertThrows<InvalidTokenException> {
            authService.validateToken(token = "invalid token")
        }
    }

    @Test
    fun `토큰 재발급 OK`() {
        val actual = authService.recreateAccessToken(accessToken = "this-is-test-token")
        val expected = "this-is-test-token"

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `토큰 재발급 중 토큰 유효성 검사 실패 Invalid Token Exception`() {
        assertThrows<InvalidTokenException> {
            authService.recreateAccessToken(accessToken = "invalid token")
        }
    }

    @Test
    fun `비밀번호 변경 OK`() {
        authService.changePassword(
            token = "this-is-test-token",
            newPassword = "newPassword",
            confirmNewPassword = "newPassword",
        )
    }

    @Test
    fun `비밀번호 변경 중 토큰 유효성 검사 실패 Invalid Token Exception`() {
        assertThrows<InvalidTokenException> {
            authService.changePassword(
                token = "invalid token",
                newPassword = "newPassword",
                confirmNewPassword = "newPassword",
            )
        }
    }

    @Test
    fun `새 비밀번호와 비밀번호 다시입력이 서로 같지 않음 Account Information Mismatch Exception`() {
        assertThrows<AccountInformationMismatchException> {
            authService.changePassword(
                token = "this-is-test-token",
                newPassword = "newPassword",
                confirmNewPassword = "void",
            )
        }
    }

    @Test
    fun `인증번호 확인 OK`() {
        authService.validateAuthenticationNumber("kotlin-good")
    }

    @Test
    fun `잘못된 인증번호 Authentication Number Mismatch Exception`() {
        assertThrows<AuthenticationNumberMismatchException> {
            authService.validateAuthenticationNumber("kotlin-bad")
        }
    }

    @Test
    fun `회원가입 OK`() {
        authService.join(
            teacherId = "teacherId2",
            teacherPassword = "teacherPassword2",
            teacherConfirmPassword = "teacherPassword2",
            teacherName = "teacherName2",
        )
    }

    @Test
    fun `이미 존재하는 아이디로 회원가입 Already Exist Account Exception`() {
        assertThrows<AlreadyExistAccountException> {
            authService.join(
                teacherId = "teacherId",
                teacherPassword = "teacherPassword",
                teacherConfirmPassword = "teacherPassword",
                teacherName = "teacherName",
            )
        }
    }

    private val authService = AuthService(
        authenticationNumber = "kotlin-good",
        jwtService = mock {
            on { createToken(eq("teacherId"), any()) } doReturn "this-is-test-token"
            on { getTeacherId(eq("this-is-test-token")) } doReturn "teacherId"
            on { isValid(eq("this-is-test-token")) } doReturn true
        },
        teacherRepository = mock {
            onGeneric { save(any()) } doReturn
                    Teacher(
                        id = "teacherId",
                        password = "bcc11c9fd8ab1e74e1bc0717239f8f7be24921abca9d5f31705612834dd1d6da3111df3f7120a0e445a91d6c3158b3e09595cd5d06c034c9997d4be2de5a02ca",
                        name = "teacherName",
                        office = "teacherOffice",
                    )
            onGeneric { findTeacherById("teacherId") } doAnswer {
                if (it.arguments[0] != "teacherId") null
                else Teacher(
                        id = "teacherId",
                        password = "bcc11c9fd8ab1e74e1bc0717239f8f7be24921abca9d5f31705612834dd1d6da3111df3f7120a0e445a91d6c3158b3e09595cd5d06c034c9997d4be2de5a02ca",
                        name = "teacherName",
                        office = "teacherOffice",
                    )
            }
            onGeneric { existsById(any()) } doAnswer {
                it.arguments[0] == "teacherId"
            }
        },
        classroomRepository = mock {
            on { findByManager("teacherId") } doReturn
                    Classroom(
                        name = "testClassroom",
                        floor = Floor.THREE,
                        priority = 0,
                        manager = "teacherId"
                    )
        },
    )
}