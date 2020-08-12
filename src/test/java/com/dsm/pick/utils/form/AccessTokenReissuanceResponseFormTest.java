package com.dsm.pick.utils.form;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenReissuanceResponseFormTest {

    private AccessTokenReissuanceResponseForm form = new AccessTokenReissuanceResponseForm("accessToken", LocalDateTime.of(2003, 8, 16, 1, 2, 3));

    @Test
    void getAccessToken() {
        String accessToken = "accessToken";
        String findAccessToken = form.getAccessToken();

        assertEquals(accessToken, findAccessToken);
    }

    @Test
    void setAccessToken() {
        String accessToken = "accessTokenTest";
        form.setAccessToken(accessToken);
        String findAccessToken = form.getAccessToken();

        assertEquals(accessToken, findAccessToken);
    }

    @Test
    void getAccessTokenExpiration() {
        LocalDateTime expiration = LocalDateTime.of(2003, 8, 16, 1, 2, 3);
        LocalDateTime findAccessTokenExpiration = form.getAccessTokenExpiration();

        assertEquals(expiration, findAccessTokenExpiration);
    }

    @Test
    void setAccessTokenExpiration() {
        LocalDateTime expiration = LocalDateTime.of(2020, 8, 12, 22, 59, 36);
        form.setAccessTokenExpiration(expiration);
        LocalDateTime findAccessTokenExpiration = form.getAccessTokenExpiration();

        assertEquals(expiration, findAccessTokenExpiration);
    }
    
}