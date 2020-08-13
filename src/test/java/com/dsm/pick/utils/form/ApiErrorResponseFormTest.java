package com.dsm.pick.utils.form;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorResponseFormTest {

    @Test
    void test() {
        String error = "error";
        String message = "message";

        ApiErrorResponseForm noArgsForm = new ApiErrorResponseForm();
        noArgsForm.setError(error);
        noArgsForm.setMessage(message);

        new ApiErrorResponseForm(error, message); // 생성 가능

        String findError = noArgsForm.getError();
        String findMessage = noArgsForm.getMessage();

        assertEquals(error, findError);
        assertEquals(message, findMessage);
    }
}