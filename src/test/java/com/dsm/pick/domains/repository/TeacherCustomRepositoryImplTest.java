package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.domains.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url=jdbc:mysql://localhost:3306/testdb_saturn?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
        "spring.datasource.username=root",
        "spring.datasource.password=1111"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherCustomRepositoryImplTest {

    @Autowired
    TeacherRepository teacherRepository;
    AuthService authService = null;

    @BeforeEach
    public void beforeEach() {
        authService = new MockAuthService(teacherRepository, new JwtService());
    }

    @Test
    void findByRefreshToken() {
        String teacherId = "aaa";
        String teacherPw = "aaa";
        String teacherName = "이진혁";
        String refreshToken = authService.getRefreshToken(teacherId);

        Teacher teacher = new Teacher(teacherId, teacherPw, teacherName, refreshToken);
        authService.join(teacher);
        Teacher findTeacher = teacherRepository.findByRefreshToken(refreshToken).get();

        assertEquals(teacher.getId(), findTeacher.getId());
        assertEquals(teacher.getPw(), findTeacher.getPw());
        assertEquals(teacher.getName(), findTeacher.getName());
    }

    class MockAuthService extends AuthService {
        public MockAuthService(TeacherRepository userRepository, JwtService jwtService) {
            super(userRepository, jwtService);
        }

        @Override
        public String getRefreshToken(String id) {
            return super.getRefreshToken(id);
        }

        @Override
        public void join(Teacher teacher) {
            super.join(teacher);
        }
    }
}