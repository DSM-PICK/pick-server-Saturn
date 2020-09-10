//package com.dsm.pick.domains.repository;
//
//import com.dsm.pick.domains.domain.Teacher;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class TeacherCustomRepositoryImplTest {
//
//    @Autowired
//    TeacherRepository teacherRepository;
//
//    @Test
//    void findByRefreshToken() {
////        String teacherId = "ddd";
////        String teacherPw = "eee";
////        String teacherName = "fff";
////
////        Teacher teacher = new Teacher();
////        teacher.setId(teacherId);
////        teacher.setPw(teacherPw);
////        teacher.setName(teacherName);
////
////        teacherRepository.save(teacher);
////
////        String refreshToken = "adfasdf.asdfasd.asdfasdf";
////        teacher.setRefreshToken(refreshToken);
////
////        Teacher findTeacher = teacherRepository.findByRefreshToken(refreshToken).get();
////
////        assertEquals(teacher, findTeacher);
//    }
//}