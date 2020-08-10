package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.UserRepository;
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

@Service
@Transactional
public class AuthService {
    private static final String ALGORITHM = "SHA-512";
    private static final String ENCODING = "UTF-8";

    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponseForm login(Teacher teacher) {
        String userId = teacher.getId();
        String userPw = sha512(teacher.getPw());

        Teacher findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IdOrPasswordMismatchException());

        String findUserPw = findUser.getPw();
        if (!(userPw.equals(findUserPw)))
            throw new IdOrPasswordMismatchException();

        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);
        LocalDateTime accessTokenExpiration = LocalDateTime.ofInstant(jwtService.getExpiration(accessToken).toInstant(), ZoneId.of("Asia/Seoul"));

        findUser.setRefreshToken(refreshToken);

        return new LoginResponseForm(accessToken, refreshToken, accessTokenExpiration);
    }

    private String sha512(String original) {
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
        return resultHex;
    }

    public AccessTokenReissuanceResponseForm accessTokenReissuance(String refreshToken) {
        Teacher findUser = null;
        if(jwtService.isUsableToken(refreshToken))
            findUser = userRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new TokenExpirationException());

        String accessToken = null;
        if(refreshToken.equals(findUser.getRefreshToken()))
            accessToken = jwtService.createAccessToken(findUser.getId());
        else
            throw new RefreshTokenMismatchException();

        LocalDateTime accessTokenExpiration = LocalDateTime.ofInstant(jwtService.getExpiration(accessToken).toInstant(), ZoneId.of("Asia/Seoul"));

        return new AccessTokenReissuanceResponseForm(accessToken, accessTokenExpiration);
    }

    public void logout(String id, String accessToken) {
        String refreshToken = null;
        if(jwtService.isUsableToken(accessToken))
            refreshToken = userRepository.findById(id)
                    .orElseThrow(() -> new TokenExpirationException()).getRefreshToken();
        jwtService.killToken(refreshToken);
        jwtService.killToken(accessToken);
    }

    public void join(Teacher teacher) {
        teacher.setPw(sha512(teacher.getPw()));
        userRepository.save(teacher);
    }
}