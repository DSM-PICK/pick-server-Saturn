package com.dsm.pick.utils.handler;

import antlr.Token;
import com.dsm.pick.utils.exception.*;
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

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponseForm> numberFormatExceptionHandler(NumberFormatException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Number Format Exception", "요청의 값이 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenInvalidExceptionHandler(TokenInvalidException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Token Invalid Exception", "토큰이 잘못 됨");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NonExistIdOrPasswordException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistIdOrPasswordExceptionHandler(NonExistIdOrPasswordException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist ID or Password Exception", "아이디 또는 비밀번호가 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<ApiErrorResponseForm> activityNotFoundExceptionHandler(ActivityNotFoundException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Activity Not Found Exception", "Activity 테이블에서 원하는 값을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonExistEncodingOrCryptographicAlgorithmException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistEncodingOrCryptographicAlgorithmExceptionHandler(NonExistEncodingOrCryptographicAlgorithmException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist ENCODING or ALGORITHM Exception", "인코딩 방식이나 암호화 알고리즘이 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.GONE);
    }
}