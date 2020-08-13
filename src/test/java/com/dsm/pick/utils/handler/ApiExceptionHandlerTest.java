package com.dsm.pick.utils.handler;

import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.RefreshTokenMismatchException;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.form.ApiErrorResponseForm;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {

    @Test
    void test() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        handler.idMismatchExceptionHandler(new IdOrPasswordMismatchException());
        handler.tokenExpirationExceptionHandler(new TokenExpirationException());
        handler.tokenExpirationExceptionHandler(new RefreshTokenMismatchException());
        handler.numberFormatExceptionHandler(new NumberFormatException());
    }
}