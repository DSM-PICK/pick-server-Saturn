package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.exception.TokenExpirationException;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.awt.font.TextHitInfo;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class AuthServiceTest {

    private TeacherRepository teacherRepository = new MockTeacherRepository(null);
    private JwtService jwtService = new JwtService();
    private AuthService authService = new AuthService(teacherRepository, jwtService);

    @Test
    void encodingPassword_단방향_해시_암호화_체크() {
        String originalPassword = "apple";

        String encodedPassword1 = authService.encodingPassword(originalPassword);
        String encodedPassword2 = authService.encodingPassword(originalPassword);

        assertEquals(encodedPassword1, encodedPassword2);
    }

    @Test
    void encodingPassword_직접_해시화_vs_AuthService_해시화() {
        String originalPassword = "banana";

        String directlyEncodedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(originalPassword.getBytes("UTF-8"));
            directlyEncodedPassword = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch(Exception e) {}

        String methodEncodedPassword = authService.encodingPassword(originalPassword);

        assertEquals(directlyEncodedPassword, methodEncodedPassword);
    }

    @Test
    void getAccessToken() {
        String teacherId = "melon";

        String findAccessToken1 = authService.getAccessToken(teacherId);
        String findAccessToken2 = authService.getAccessToken(teacherId);

        assertEquals(findAccessToken1, findAccessToken2);
    }

    @Test
    void getRefreshToken() {
        String teacherId = "orange";

        String findRefreshToken1 = authService.getRefreshToken(teacherId);
        String findRefreshToken2 = authService.getRefreshToken(teacherId);

        assertEquals(findRefreshToken1, findRefreshToken2);
    }

    @Test
    void getAccessTokenExpiration() {
        String teacherId = "strawberry";
        
        String accessToken = authService.getAccessToken(teacherId);

        LocalDateTime accessTokenExpiration1 = authService.getAccessTokenExpiration(accessToken);
        LocalDateTime accessTokenExpiration2 = authService.getAccessTokenExpiration(accessToken);

        assertEquals(accessTokenExpiration1, accessTokenExpiration2);
    }

    @Test
    void getSameRefreshTokenTeacher_TokenExpirationException_발생() {
        String refreshToken = "watermelon";

        assertThrows(TokenExpirationException.class, () -> authService.getSameRefreshTokenTeacher(refreshToken));
    }

    @Test
    void getSameRefreshTokenTeacher() {
//        String teacherId = "grape";
//        String teacherPw = "grape";
//        String teacherName = "grape";
//
//        Teacher teacher = new Teacher();
//        teacher.setId(teacherId);
//        teacher.setPw(teacherPw);
//        teacher.setName(teacherName);
//
//        authService.join(teacher);
//
//        if(authService.checkIdAndPw(teacher)) {
//            Teacher findTeacher = authService.getSameRefreshTokenTeacher(teacher.getRefreshToken());
//
//            assertEquals(findTeacher.getRefreshToken(), teacher.getRefreshToken());
//        }
    }

    @Test
    void checkIdAndPw() {
    }

    @Test
    void formatLoginResponseForm() {
    }

    @Test
    void formatAccessTokenReissuanceResponseForm() {
    }

    @Test
    void logout() {
    }

    @Test
    void join() {
    }

    static class MockTeacherRepository extends TeacherRepository {

        Map<String, Teacher> teachers = new HashMap<>();

        public MockTeacherRepository(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        public void save(Teacher teacher) {
            teachers.put(teacher.getId(), teacher);
        }

        @Override
        public Optional<Teacher> findById(String id) {
            Teacher findTeacher = teachers.get(id);
            return Optional.ofNullable(findTeacher);
        }

        @Override
        public Optional<Teacher> findByRefreshToken(String refreshToken) {
            return teachers.values()
                    .stream()
                    .filter(t -> t.getRefreshToken().equals(refreshToken))
                    .findAny();
        }
    }
}