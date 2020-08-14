package com.dsm.pick.controller;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.domains.service.AuthService;
import com.dsm.pick.domains.service.JwtService;
import com.dsm.pick.utils.form.AccessTokenReissuanceResponseForm;
import com.dsm.pick.utils.form.LoginResponseForm;
import com.dsm.pick.utils.form.TeacherResponseForm;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthService authService = new MockAuthService(null, null);
    private AuthController authController = new AuthController(authService);

    @Test
    void login_success() {
        String teacherId = "aaa";
        String teacherPw = "bbb";
        TeacherResponseForm teacherResponseForm = new TeacherResponseForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        String expectedAccessToken = "987654321" + teacherId;
        String expectedRefreshToken = "123456789" + teacherId;
        LocalDateTime expectedAccessTokenExpiration = LocalDateTime.of(2003, 8, 16, 1, 2, 3);

        assertEquals(expectedAccessToken, actual.getAccessToken());
        assertEquals(expectedRefreshToken, actual.getRefreshToken());
        assertEquals(expectedAccessTokenExpiration, actual.getAccessTokenExpiration());
    }

    @Test
    void login_password_mismatch_return_null() {
        String teacherId = "aaa";
        String teacherPw = "null";
        TeacherResponseForm teacherResponseForm = new TeacherResponseForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        assertNull(actual);
    }

    @Test
    void login_id_mismatch_return_null() {
        String teacherId = "null";
        String teacherPw = "bbb";
        TeacherResponseForm teacherResponseForm = new TeacherResponseForm(teacherId, teacherPw);

        LoginResponseForm actual = authController.login(teacherResponseForm);

        assertNull(actual);
    }

    @Test
    void accessTokenReissuance() {
        login_success();


    }

    @Test
    void logout() {
    }

    static class MockAuthService extends AuthService {

        private Map<String, Teacher> teachers = new HashMap<>();

        public MockAuthService(TeacherRepository userRepository, JwtService jwtService) {
            super(userRepository, jwtService);

            Teacher teacher = new Teacher();
            teacher.setId("aaa");
            teacher.setPw("bbb");
            teacher.setName("ccc");
            teachers.put(teacher.getId(), teacher);
        }

        @Override
        public String encodingPassword(String original) {
            return original;
        }

        @Override
        public String getAccessToken(String id) {
            return "987654321" + id;
        }

        @Override
        public String getRefreshToken(String id) {
            return "123456789" + id;
        }

        @Override
        public LocalDateTime getAccessTokenExpiration(String accessToken) {
            return LocalDateTime.of(2003, 8, 16, 1, 2, 3);
        }

        @Override
        public Teacher getSameRefreshTokenTeacher(String refreshToken) {
            return super.getSameRefreshTokenTeacher(refreshToken);
        }

        @Override
        public boolean checkIdAndPw(Teacher teacher) {
            Teacher findTeacher = teachers.get(teacher.getId());
            teachers.remove(teacher.getId());
            if(findTeacher == null) return false;
            else if(!findTeacher.getPw().equals(teacher.getPw())) return false;
            findTeacher.setRefreshToken(getRefreshToken(teacher.getId()));
            teachers.put(teacher.getId(), findTeacher);
            return true;
        }

        @Override
        public LoginResponseForm formatLoginResponseForm(String accessToken, String refreshToken, LocalDateTime accessTokenExpiration) {
            return new LoginResponseForm(accessToken, refreshToken, accessTokenExpiration);
        }

        @Override
        public AccessTokenReissuanceResponseForm formatAccessTokenReissuanceResponseForm(String accessToken, LocalDateTime accessTokenExpiration) {
            return new AccessTokenReissuanceResponseForm(accessToken, accessTokenExpiration);
        }

        @Override
        public void logout(String accessToken) {
            String teacherId = accessToken.substring(9);
            Teacher findTeacher = teachers.get(teacherId);
            teachers.remove(teacherId);
            findTeacher.setRefreshToken(null);
            teachers.put(findTeacher.getId(), findTeacher);
        }
    }
}