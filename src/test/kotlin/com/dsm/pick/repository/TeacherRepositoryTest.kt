package com.dsm.pick.repository

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TeacherRepositoryTest(
    private val teacherRepository: TeacherRepository
) {

    @Test
    fun `아이디로 선생님 조회 OK`() {
        val teacher = teacherRepository.findTeacherById("teacherId")

        assertThat(teacher?.id).isEqualTo("teacherId")
        assertThat(teacher?.password).isEqualTo("bcc11c9fd8ab1e74e1bc0717239f8f7be24921abca9d5f31705612834dd1d6da3111df3f7120a0e445a91d6c3158b3e09595cd5d06c034c9997d4be2de5a02ca")
        assertThat(teacher?.name).isEqualTo("teacherName")
        assertThat(teacher?.office).isEqualTo("teacherOffice")
    }

    @Test
    fun `없는 아이디로 선생님 조회 OK`() {
        assertThat(teacherRepository.findTeacherById("없는선생님아이디")).isNull()
    }
}