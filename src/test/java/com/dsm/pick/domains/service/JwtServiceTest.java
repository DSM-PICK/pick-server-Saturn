package com.dsm.pick.domains.service;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    JwtService jwtService = new JwtService();

    @Test
    void createAccessToken() {
        String teacherId = "root";
        String accessToken = jwtService.createAccessToken(teacherId);
        String findTeacherId = Jwts.parser()
                .setSigningKey("dhwlddjgmanf")
                .parseClaimsJws(accessToken)
                .getBody()
                .get("id", String.class);

        Assertions.assertEquals(teacherId, findTeacherId);
    }

    @Test
    void createRefreshToken() {
    }

    @Test
    void getTeacherId() {
    }

    @Test
    void getExpiration() {
    }

    @Test
    void isUsableToken() {
    }

    @Test
    void killToken() {
    }
}