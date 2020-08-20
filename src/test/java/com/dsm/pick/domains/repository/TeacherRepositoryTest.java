package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {

    @Autowired
    EntityManager entityManager;

    TeacherRepository teacherRepository;

    @BeforeEach
    void beforeEach() {
        teacherRepository = new TeacherRepository(entityManager);
    }

    @Test
    void save_AND_findById() {
        String teacherId = "aaa";
        String teacherPw = "bbb";
        String teacherName = "ccc";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        teacherRepository.save(teacher);

        assertEquals(teacher, teacherRepository.findById(teacherId).get());
    }

    @Test
    void findByRefreshToken() {
        String teacherId = "ddd";
        String teacherPw = "eee";
        String teacherName = "fff";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        teacherRepository.save(teacher);

        String refreshToken = "adfasdf.asdfasd.asdfasdf";
        teacher.setRefreshToken(refreshToken);

        Teacher findTeacher = teacherRepository.findByRefreshToken(refreshToken).get();

        assertEquals(teacher, findTeacher);
    }
}