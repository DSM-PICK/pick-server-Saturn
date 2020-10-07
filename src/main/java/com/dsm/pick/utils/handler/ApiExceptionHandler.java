package com.dsm.pick.utils.handler;

import antlr.Token;
import com.dsm.pick.utils.exception.IdOrPasswordMismatchException;
import com.dsm.pick.utils.exception.NonExistIdOrPasswordException;
import com.dsm.pick.utils.exception.RefreshTokenMismatchException;
import com.dsm.pick.utils.exception.TokenInvalidException;
import com.dsm.pick.utils.form.ApiErrorResponseForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IdOrPasswordMismatchException.class)
    public ResponseEntity<ApiErrorResponseForm> idOrPasswordMismatchExceptionHandler(IdOrPasswordMismatchException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("ID or Password Mismatch Exception", "아이디 또는 비밀번호가 일치하는 계정을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenMismatchException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenExpirationExceptionHandler(RefreshTokenMismatchException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Refresh Token Mismatch Exception", "일치하는 리프레시 토큰을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponseForm> numberFormatExceptionHandler(NumberFormatException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Number Format Exception", "요청에 대한 적절한 응답을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenInvalidExceptionHandler(TokenInvalidException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Token Invalid Exception", "토큰이 만료되었거나 잘못되었음");
        return new ResponseEntity<>(response, HttpStatus.GONE);
    }

    @ExceptionHandler(NonExistIdOrPasswordException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistIdOrPasswordExceptionHandler(NonExistIdOrPasswordException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist ID or Password Exception", "아이디 또는 비밀번호가 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }
}