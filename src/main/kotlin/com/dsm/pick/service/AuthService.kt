package com.dsm.pick.service

import com.dsm.pick.controller.response.LoginResponse
import com.dsm.pick.controller.response.LoginResponse.ManagedClassroom
import com.dsm.pick.domain.Teacher
import com.dsm.pick.exception.AccountInformationMismatchException
import com.dsm.pick.exception.AlreadyExistAccountException
import com.dsm.pick.exception.AuthenticationNumberMismatchException
import com.dsm.pick.exception.InvalidTokenException
import com.dsm.pick.repository.ClassroomRepository
import com.dsm.pick.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

@Service
@Transactional
class AuthService(
    @Value("\${JOIN_AUTHENTICATION_NUMBER:kotlin-good}")
    private val authenticationNumber: String,
    private val jwtService: JwtService,
    private val teacherRepository: TeacherRepository,
    private val classroomRepository: ClassroomRepository,
) {
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")

    fun login(teacherId: String, teacherPassword: String): LoginResponse {
        validateAccountInformation(teacherId, teacherPassword)
        return LoginResponse(
            accessToken = createAccessToken(teacherId),
            refreshToken = createRefreshToken(teacherId),
            teacherName = findTeacherById(teacherId).name,
            managedClassroom = findManagedClassroom(teacherId)
                .let {
                    if (it == null) null
                    else ManagedClassroom(
                        name = it.name,
                        floor = it.floor.value,
                        priority = it.priority,
                    )
                }
        )
    }

    fun validateToken(token: String) {
        val isValid = jwtService.isValid(token)
        if (!isValid) throw InvalidTokenException(token)
    }

    fun recreateAccessToken(accessToken: String): String {
        validateToken(accessToken)

        val teacherId = findTeacherIdByToken(accessToken)
        return createAccessToken(teacherId)
    }

    fun changePassword(token: String, newPassword: String, confirmNewPassword: String) {
        validateToken(token)

        val teacherId = findTeacherIdByToken(token)
        val teacher = findTeacherById(teacherId)
        validateSamePassword(newPassword, confirmNewPassword)

        teacher.password = encodingPassword(newPassword)
    }

    fun validateAuthenticationNumber(authenticationNumber: String) {
        if (this.authenticationNumber != authenticationNumber)
            throw AuthenticationNumberMismatchException(authenticationNumber)
    }

    fun join(teacherId: String, teacherPassword: String, teacherConfirmPassword: String, teacherName: String) {
        validateSamePassword(teacherPassword, teacherConfirmPassword)

        val isJoinPossible = isJoinPossible(teacherId)
        if (isJoinPossible)
            teacherRepository.save(
                Teacher(
                    id = teacherId,
                    password = encodingPassword(teacherPassword),
                    name = teacherName,
                    office = "임시 교무실",
                )
            )
        else
            throw AlreadyExistAccountException(teacherId)
    }

    private fun validateAccountInformation(teacherId: String, teacherPassword: String) {
        val teacher = findTeacherById(teacherId)
        val encodedPassword = encodingPassword(teacherPassword)
        validateSamePassword(teacher.password, encodedPassword)
    }

    private fun findTeacherById(teacherId: String) = teacherRepository.findByIdOrNull(teacherId)?: throw AccountInformationMismatchException(teacherId, "찾은 정보 없음")

    private fun encodingPassword(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }

    private fun validateSamePassword(requestPassword: String, findPassword: String) {
        if(requestPassword != findPassword)
            throw AccountInformationMismatchException(requestPassword, findPassword)
    }

    private fun createAccessToken(teacherId: String) = jwtService.createToken(teacherId, Token.ACCESS)

    private fun createRefreshToken(teacherId: String) = jwtService.createToken(teacherId, Token.REFRESH)

    private fun findManagedClassroom(teacherId: String) = classroomRepository.findByManager(teacherId)

    private fun findTeacherIdByToken(token: String) = jwtService.getTeacherId(token)

    private fun isJoinPossible(teacherId: String) = teacherRepository.existsById(teacherId)
}