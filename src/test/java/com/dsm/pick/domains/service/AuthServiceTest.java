package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.form.AccessTokenReissuanceResponseForm;
import com.dsm.pick.utils.form.LoginResponseForm;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private TeacherRepository teacherRepository = new MockTeacherRepository();
    private JwtService jwtService = new MockJwtService();
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
    void getSameRefreshTokenTeacher_checkIdAndPw() {
        String teacherId = "grape";
        String teacherPw = "grape";
        String teacherName = "grape";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        authService.join(teacher);

        if(authService.checkIdAndPw(teacher)) {
            Teacher findTeacher = authService.getSameRefreshTokenTeacher(teacher.getRefreshToken());

            assertEquals(findTeacher.getRefreshToken(), teacher.getRefreshToken());
        }
    }

    @Test
    void checkIdAndPw_IdMismatchException_발생() {
        String teacherId = "mango";
        String teacherPw = "mango";
        String teacherName = "mango";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        assertThrows(IdOrPasswordMismatchException.class, () -> authService.checkIdAndPw(teacher));
    }

    @Test
    void checkIdAndPw_PasswordMismatchException_발생() {
        String teacherId = "raspberry";
        String teacherPw = "raspberry";
        String teacherName = "raspberry";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        authService.join(teacher);

        String wrongPw = "hello";

        Teacher targetTeacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(wrongPw);
        teacher.setName(teacherName);

        assertThrows(IdOrPasswordMismatchException.class, () -> authService.checkIdAndPw(targetTeacher));
    }

    @Test
    void formatLoginResponseForm() {
        String accessToken = "987654321peach";
        String refreshToken = "123456789peach";
        LocalDateTime expiration = LocalDateTime.of(2003, 8, 16, 1, 2, 3);

        LoginResponseForm directlyForm = new LoginResponseForm(accessToken, refreshToken, expiration);
        LoginResponseForm form = authService.formatLoginResponseForm(accessToken, refreshToken, expiration);

        assertEquals(directlyForm.getAccessToken(), form.getAccessToken());
        assertEquals(directlyForm.getRefreshToken(), form.getRefreshToken());
        assertEquals(directlyForm.getAccessTokenExpiration(), form.getAccessTokenExpiration());
    }

    @Test
    void formatAccessTokenReissuanceResponseForm() {
        String teacherId = "peach";
        String accessToken = authService.getAccessToken(teacherId);
        LocalDateTime expiration = LocalDateTime.of(2003, 8, 16, 1, 2, 3);

        AccessTokenReissuanceResponseForm directlyForm = new AccessTokenReissuanceResponseForm(accessToken, expiration);
        AccessTokenReissuanceResponseForm form = authService.formatAccessTokenReissuanceResponseForm(accessToken, expiration);

        assertEquals(directlyForm.getAccessToken(), form.getAccessToken());
        assertEquals(directlyForm.getAccessTokenExpiration(), form.getAccessTokenExpiration());
    }

    @Test
    void logout_정상적인_토큰이_아님으로_인한_TokenExpirationException_발생() {
        String accessToken = "pineapple";

        assertThrows(TokenExpirationException.class, () -> authService.logout(accessToken));
    }

    @Test
    void logout_토큰에_해당하는_Teacher가_없음으로_인한_TokenExpirationException_발생() {
        String teacherId = "plum";
        String accessToken = authService.getAccessToken(teacherId);

        assertThrows(TokenExpirationException.class, () -> authService.logout(accessToken));
    }

    @Test
    void logout() {
        String teacherId = "cherry";
        String teacherPw = "cherry";
        String teacherName = "cherry";

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setPw(teacherPw);
        teacher.setName(teacherName);

        authService.join(teacher);

        String accessToken = authService.getAccessToken(teacherId);

        authService.logout(accessToken);
    }

    @Test
    void join() {
    }

//    static class MockTeacherRepository extends TeacherRepository {
//
//        Map<String, Teacher> teachers = new HashMap<>();
//
//        public MockTeacherRepository(EntityManager entityManager) {
//            super(entityManager);
//        }
//
//        @Override
//        public void save(Teacher teacher) {
//            teachers.put(teacher.getId(), teacher);
//        }
//
//        @Override
//        public Optional<Teacher> findById(String id) {
//            Teacher findTeacher = teachers.get(id);
//            return Optional.ofNullable(findTeacher);
//        }
//
//        @Override
//        public Optional<Teacher> findByRefreshToken(String refreshToken) {
//            return teachers.values()
//                    .stream()
//                    .filter(t -> t.getRefreshToken().equals(refreshToken))
//                    .findAny();
//        }
//    }

    static class MockTeacherRepository implements TeacherRepository {

        Map<String, Teacher> teachers = new HashMap<>();

        @Override
        public <S extends Teacher> S save(S entity) {
            teachers.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public Optional<Teacher> findById(String s) {
            Teacher findTeacher = teachers.get(s);
            return Optional.ofNullable(findTeacher);
        }

        @Override
        public Optional<Teacher> findByRefreshToken(String refreshToken) {
            return teachers.values()
                    .stream()
                    .filter(t -> t.getRefreshToken().equals(refreshToken))
                    .findAny();
        }

        @Override
        public List<Teacher> findAll() {
            return null;
        }

        @Override
        public List<Teacher> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Teacher> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public List<Teacher> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(Teacher entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends Teacher> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public <S extends Teacher> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Teacher> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public void deleteInBatch(Iterable<Teacher> entities) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Teacher getOne(String s) {
            return null;
        }

        @Override
        public <S extends Teacher> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Teacher> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Teacher> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Teacher> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Teacher> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Teacher> boolean exists(Example<S> example) {
            return false;
        }
    }

    static class MockJwtService extends JwtService {

        private Date date = new Date();
        private String teacherId = null;

        @Override
        public String createAccessToken(String teacherId) {
            this.teacherId = teacherId;
            return "987654321" + teacherId;
        }

        @Override
        public String createRefreshToken(String teacherId) {
            this.teacherId = teacherId;
            return "123456789" + teacherId;
        }

        @Override
        public String getTeacherId(String token) {
            return token.substring(9);
        }

        @Override
        public Date getExpiration(String token) {
            return date;
        }

        @Override
        public boolean isUsableToken(String token) {
            return token.substring(9).equals(teacherId);
        }

//        @Override
//        public void killToken(String token) {
//        }
    }
}