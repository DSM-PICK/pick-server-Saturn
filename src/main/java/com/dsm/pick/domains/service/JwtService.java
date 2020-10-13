package com.dsm.pick.domains.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    //private static final String SECURE_KEY = env.get("TOKEN_SECURE_KEY");
    @Value("${TOKEN_SECURE_KEY:dhwlddjgmanf}")
    private String SECURE_KEY = "";
    private final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECURE_KEY);
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final Key KEY = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    public String createAccessToken(String teacherId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("access token")
                .claim("id", teacherId)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))             // 30분
                .signWith(signatureAlgorithm, KEY)
                .compact();
    }

    public String createRefreshToken(String teacherId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("refresh token")
                .claim("id", teacherId)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)))    // 2주
                .signWith(signatureAlgorithm, KEY)
                .compact();
    }

    public String getTeacherId(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class);
    }

    public Date getExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token);

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isNotTimeOut(String token) {
        try {
            Date now = new Date();
            Date expiration = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return expiration.after(now);
        } catch(Exception e) {
            return false;
        }
    }
}
