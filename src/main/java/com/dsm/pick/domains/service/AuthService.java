package com.dsm.pick.domains.service;

import antlr.Token;
import com.dsm.pick.domains.domain.User;
import com.dsm.pick.domains.repository.UserRepository;
import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.RefreshTokenMismatchException;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.exception.UnauthorizedException;
import com.dsm.pick.utils.form.AccessTokenReissuanceResultForm;
import com.dsm.pick.utils.form.LoginResultForm;
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

    public LoginResultForm login(User user) throws IdOrPasswordMismatchException {
        String userId = user.getId();
        String userPw = sha512(user.getPw());

        User findUser = userRepository.findById(userId).orElseThrow(() -> new IdOrPasswordMismatchException());
        String findUserPw = findUser.getPw();
        if (!(userPw.equals(findUserPw)))
            throw new IdOrPasswordMismatchException();


        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);
        LocalDateTime accessTokenExpiration = LocalDateTime.ofInstant(jwtService.getExpiration(accessToken).toInstant(), ZoneId.of("Asia/Seoul"));

        findUser.setRefreshToken(refreshToken);

        return new LoginResultForm(accessToken, refreshToken, accessTokenExpiration);
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
        System.out.println(resultHex);
        return resultHex;
    }

    public AccessTokenReissuanceResultForm accessTokenReissuance(String refreshToken) {
        User findUser = null;
        if(jwtService.isValid(refreshToken) && jwtService.isTimeOut(refreshToken))
            userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new TokenExpirationException());

        String accessToken = null;
        if(refreshToken.equals(findUser.getRefreshToken()))
            accessToken = jwtService.createAccessToken(findUser.getId());
        else
            throw new RefreshTokenMismatchException();

        LocalDateTime accessTokenExpiration = LocalDateTime.ofInstant(jwtService.getExpiration(accessToken).toInstant(), ZoneId.of("Asia/Seoul"));

        return new AccessTokenReissuanceResultForm(accessToken, accessTokenExpiration);
    }

    public void logout(String id, String accessToken) {
        String refreshToken = userRepository.findById(id).orElseThrow(() -> new UnauthorizedException()).getRefreshToken();
        jwtService.killToken(refreshToken);
        jwtService.killToken(accessToken);
    }

    public void join(User user) {
        user.setPw(sha512(user.getPw()));
        userRepository.save(user);
    }
}
