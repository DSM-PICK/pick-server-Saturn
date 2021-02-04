package com.dsm.pick.service

import com.dsm.pick.controller.response.LoginResponse
import com.dsm.pick.exception.AccountInformationMismatchException
import com.dsm.pick.exception.InvalidTokenException
import com.dsm.pick.repository.ClassRepository
import com.dsm.pick.repository.TeacherRepository
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

@Service
class AuthService(
    private val jwtService: JwtService,
    private val teacherRepository: TeacherRepository,
    private val classRepository: ClassRepository,
) {
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")

    fun login(teacherId: String, teacherPassword: String): LoginResponse {
        validateAccountInformation(teacherId, teacherPassword)
        return LoginResponse(
            accessToken = createAccessToken(teacherId),
            refreshToken = createRefreshToken(teacherId),
            teacherName = findTeacherById(teacherId).name,
            managedClassroom = findManagedClassroom(teacherId)?.name,
        )
    }

    fun validateToken(token: String) {
        val isValid = jwtService.isValid(token)
        if (!isValid) throw InvalidTokenException(token)
    }

    fun recreateAccessToken(accessToken: String): String {
        validateToken(accessToken)
        val teacherId = jwtService.getTeacherId(accessToken)
        return createAccessToken(teacherId)
    }

    private fun validateAccountInformation(teacherId: String, teacherPassword: String) {
        val teacher = findTeacherById(teacherId)
        val encodedPassword = encodingPassword(teacherPassword)
        validateSamePassword(teacher.password, encodedPassword)
    }

    private fun findTeacherById(teacherId: String) = teacherRepository.findById(teacherId).orElseThrow { AccountInformationMismatchException(teacherId, "찾은 정보 없음") }

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

    private fun findManagedClassroom(teacherId: String) = classRepository.findByManager(teacherId)
}