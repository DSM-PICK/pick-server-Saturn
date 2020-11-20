//package com.dsm.pick.domains.service;
//
//import io.jsonwebtoken.Jwt;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JwtServiceTest {
//
//    JwtService jwtService = new JwtService("dhwlddjgmanf");
//
//    @Test
//    void createAccessToken() {
//        String teacherId = "kiwi";
//        String accessToken = jwtService.createAccessToken(teacherId);
//    }
//
//    @Test
//    void createRefreshToken() {
//        String teacherId = "lemon";
//        String refreshToken = jwtService.createRefreshToken(teacherId);
//    }
//
//    @Test
//    void getTeacherId() {
//        String teacherId = "richie";
//        String accessToken = jwtService.createAccessToken(teacherId);
//
//        String findTeacherId = jwtService.getTeacherId(accessToken);
//
//        assertEquals(teacherId, findTeacherId);
//    }
//
//    @Test
//    void getExpiration() {
//        String teacherId = "quince";
//        String accessToken = jwtService.createAccessToken(teacherId);
//        Date expiration = jwtService.getExpiration(accessToken);
//    }
//
//    @Test
//    void isUsableToken() {
//        String teacherId = "jujube";
//        String accessToken = jwtService.createAccessToken(teacherId);
//
//        boolean isValid = jwtService.isValid(accessToken);
//        boolean isTimeOut = jwtService.isNotTimeOut(accessToken);
//
//        assertTrue(isValid && isTimeOut);
//    }
//}