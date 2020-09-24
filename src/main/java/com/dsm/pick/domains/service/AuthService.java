package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.RefreshTokenMismatchException;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.form.AccessTokenReissuanceResponseForm;
import com.dsm.pick.utils.form.LoginResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
public class AuthService {
    private static final String ALGORITHM = "SHA-512";
    private static final String ENCODING = "UTF-8";

    private TeacherRepository teacherRepository;
    private JwtService jwtService;

    @Autowired
    public AuthService(TeacherRepository userRepository, JwtService jwtService) {
        this.teacherRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String encodingPassword(String original) {
        String resultHex = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(original.getBytes(ENCODING));
            resultHex = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch(Exception e) {
            System.out.println("존재하지 않는 인코딩 : " + ENCODING);
            System.out.println("존재하지 않는 암호화 : " + ALGORITHM);
        }
        System.out.println(original + " -> " + resultHex);
        return resultHex;
    }

    private void setTeacherRefreshToken(Teacher teacher) {
        String refreshToken = getRefreshToken(teacher.getId());
        teacher.setRefreshToken(refreshToken);
    }

    public String getAccessToken(String id) {
        return jwtService.createAccessToken(id);
    }

    public String getRefreshToken(String id) {
        return jwtService.createRefreshToken(id);
    }

    public LocalDateTime getAccessTokenExpiration(String accessToken) {
        Date expiration = jwtService.getExpiration(accessToken);
        return LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.of("Asia/Seoul"));
    }

    public Teacher getSameRefreshTokenTeacher(String refreshToken) {
        if(jwtService.isUsableToken(refreshToken)) {
            Teacher findTeacher = teacherRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new RefreshTokenMismatchException());

            String findRefreshToken = findTeacher.getRefreshToken();
            if(refreshToken.equals(findRefreshToken))
                return findTeacher;
            else
                throw new RefreshTokenMismatchException();
        } else {
            throw new TokenExpirationException();
        }
    }

    public boolean checkIdAndPw(Teacher teacher) {
        // 계정 존재하는지 확인
        String userId = teacher.getId();

        Teacher findTeacher = teacherRepository.findById(userId)
                .orElseThrow(() -> new IdOrPasswordMismatchException());

        // 비밀번호 일치 확인
        String userPw = teacher.getPw();
        String findUserPw = findTeacher.getPw();

        if (!(userPw.equals(findUserPw)))
            throw new IdOrPasswordMismatchException();

        // 선생님에게 리프레시 토큰 저장
        setTeacherRefreshToken(findTeacher);

        return true;
    }

    public LoginResponseForm formatLoginResponseForm(String accessToken, String refreshToken, LocalDateTime accessTokenExpiration) {
        return new LoginResponseForm(accessToken, refreshToken, accessTokenExpiration);
    }

    public AccessTokenReissuanceResponseForm formatAccessTokenReissuanceResponseForm(String accessToken, LocalDateTime accessTokenExpiration) {
        return new AccessTokenReissuanceResponseForm(accessToken, accessTokenExpiration);
    }

    public void logout(String accessToken) {
        if(jwtService.isUsableToken(accessToken)) {
            String teacherId = jwtService.getTeacherId(accessToken);

            Teacher findTeacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new TokenExpirationException());

//            String refreshToken = findTeacher.getRefreshToken();

//            jwtService.killToken(refreshToken);
//            jwtService.killToken(accessToken);

            findTeacher.setRefreshToken(null);
        } else {
            throw new TokenExpirationException();
        }
    }

    public void join(Teacher teacher) {
        teacher.setPw(encodingPassword(teacher.getPw()));
        teacherRepository.save(teacher);
    }
}