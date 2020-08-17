package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:application.yml"})
@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void save() {
        Teacher teacher = new Teacher();
        teacher.setId("1234");
        teacher.setPw("1234");
        teacher.setName("1234");
        teacherRepository.save(teacher);
    }

    @Test
    void findById() {
        Teacher teacher1 = new Teacher();
        teacher1.setId("aaa");
        teacher1.setPw("aaa");
        teacher1.setName("aaa");
        teacherRepository.save(teacher1);

        Teacher teacher = teacherRepository.findById("aaa").get();
        System.out.println(teacher.getId());
        System.out.println(teacher.getPw());
        System.out.println(teacher.getName());
    }

    @Test
    void findByRefreshToken() {
    }
}