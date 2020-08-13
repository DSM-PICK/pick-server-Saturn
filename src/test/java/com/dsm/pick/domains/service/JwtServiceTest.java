package com.dsm.pick.domains.service;

import com.dsm.pick.utils.exception.TokenExpirationException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final String SECURE_KEY = "dhwlddjgmanf";
    private static final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECURE_KEY);
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final Key KEY = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    JwtService jwtService = new JwtService();

    @Test
    void createAccessToken() {
        String teacherId = "kiwi";
        String accessToken = jwtService.createAccessToken(teacherId);
    }

    @Test
    void createRefreshToken() {
        String teacherId = "lemon";
        String refreshToken = jwtService.createRefreshToken(teacherId);
    }

    @Test
    void getTeacherId() {
        String teacherId = "richie";
        String accessToken = jwtService.createAccessToken(teacherId);

        String findTeacherId = jwtService.getTeacherId(accessToken);

        assertEquals(teacherId, findTeacherId);
    }

    @Test
    void getExpiration() {
        String teacherId = "quince";
        String accessToken = jwtService.createAccessToken(teacherId);
        Date expiration = jwtService.getExpiration(accessToken);
    }

    @Test
    void isUsableToken() {
        String teacherId = "jujube";
        String accessToken = jwtService.createAccessToken(teacherId);

        boolean usableToken = jwtService.isUsableToken(accessToken);

        assertTrue(usableToken);
    }

    @Test
    void isUsableToken_죽은_토큰으로_인한_TokenExpirationException_발생() {
//        String teacherId = "citron";
//        String accessToken = jwtService.createAccessToken(teacherId);
//
//        jwtService.killToken(accessToken);
//        try {
//            Thread.sleep(5000);
//        } catch(Exception e) {}
//        boolean usableToken = jwtService.isUsableToken(accessToken);
//
//        assertFalse(usableToken);
    }

//    @Test
//    void killToken() {
//        String teacherId = "avocado";
//
//        String accessToken = jwtService.createAccessToken(teacherId);
//
//        jwtService.killToken(accessToken);
//    }
}