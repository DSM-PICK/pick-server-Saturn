package com.dsm.pick.utils.form;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenReissuanceResponseFormTest {

    @Test
    void test() {
        String accessToken = "accessToken";
        LocalDateTime expiration = LocalDateTime.now();

        AccessTokenReissuanceResponseForm noArgsForm = new AccessTokenReissuanceResponseForm();
        noArgsForm.setAccessToken(accessToken);
        noArgsForm.setAccessTokenExpiration(expiration);

        new AccessTokenReissuanceResponseForm(accessToken, expiration); // 생성 가능

        String findAccessToken = noArgsForm.getAccessToken();
        LocalDateTime findExpiration = noArgsForm.getAccessTokenExpiration();

        assertEquals(accessToken, findAccessToken);
        assertEquals(expiration, findExpiration);
    }
}