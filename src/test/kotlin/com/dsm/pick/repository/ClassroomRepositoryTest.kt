package com.dsm.pick.repository

import com.dsm.pick.domain.attribute.Floor
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ClassroomRepositoryTest(
    private val classroomRepository: ClassroomRepository
) {

    @Test
    fun `담당자로 교실 조회 OK`() {
        val classroom = classroomRepository.findByManager("teacherId")!!

        assertThat(classroom.name).isEqualTo("testClassroom")
        assertThat(classroom.floor).isEqualTo(Floor.THREE)
        assertThat(classroom.priority).isEqualTo(0)
        assertThat(classroom.manager).isEqualTo("teacherId")
        assertThat(classroom.students).map<String> { it.number }.isEqualTo(listOf("3417"))
    }

    @Test
    fun `없는 담당자로 교실 조회 OK`() {
        assertThat(classroomRepository.findByManager("void")).isNull()
    }

    @Test
    fun `층으로 교실 조회 OK`() {
        assertThat(classroomRepository.findByFloor(Floor.THREE).map { it.name }).isEqualTo(listOf("testClassroom"))
    }

    @Test
    fun `없는 층으로 교실 조회 OK`() {
        assertThat(classroomRepository.findByFloor(Floor.TWO)).isEmpty()
    }

    @Test
    fun `층과 우선순위로 교실 조회 OK`() {
        val classroom = classroomRepository.findByFloorAndPriority(Floor.THREE, 0)!!

        assertThat(classroom.name).isEqualTo("testClassroom")
        assertThat(classroom.floor).isEqualTo(Floor.THREE)
        assertThat(classroom.priority).isEqualTo(0)
        assertThat(classroom.manager).isEqualTo("teacherId")
        assertThat(classroom.students).map<String> { it.number }.isEqualTo(listOf("3417"))
    }

    @Test
    fun `없는 층과 우선순위로 교실 조회 OK`() {
        assertThat(classroomRepository.findByFloorAndPriority(Floor.TWO, 10)).isNull()
    }
}