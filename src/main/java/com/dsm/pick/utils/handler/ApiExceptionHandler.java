package com.dsm.pick.utils.handler;

import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.RefreshTokenMismatchException;
import com.dsm.pick.utils.exception.TokenExpirationException;
import com.dsm.pick.utils.form.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IdOrPasswordMismatchException.class)
    public ResponseEntity<ApiErrorResponse> idMismatchExceptionHandler(IdOrPasswordMismatchException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ID Mismatch Exception", "아이디가 일치하는 계정을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpirationException.class)
    public ResponseEntity<ApiErrorResponse> tokenExpirationExceptionHandler(TokenExpirationException ex) {
        ApiErrorResponse response = new ApiErrorResponse("Token Expiration Exception", "토큰이 만료되거나 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenMismatchException.class)
    public ResponseEntity<ApiErrorResponse> tokenExpirationExceptionHandler(RefreshTokenMismatchException ex) {
        ApiErrorResponse response = new ApiErrorResponse("Refresh Token Mismatch Exception", "일치하는 리프레시 토큰을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}