package com.dsm.pick.domains.service;

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