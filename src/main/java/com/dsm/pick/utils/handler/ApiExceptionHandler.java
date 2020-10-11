package com.dsm.pick.utils.handler;

import com.dsm.pick.utils.exception.*;
import com.dsm.pick.utils.form.ApiErrorResponseForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IdOrPasswordMismatchException.class)
    public ResponseEntity<ApiErrorResponseForm> idOrPasswordMismatchExceptionHandler(IdOrPasswordMismatchException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("ID or Password Mismatch Exception", "아이디 또는 비밀번호가 일치하는 계정을 찾을 수 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<ApiErrorResponseForm> clubNotFoundExceptionHandler(ClubNotFoundException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Club Not Found Exception", "조건에 맞는 클럽을 찾을 수가 없음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponseForm> numberFormatExceptionHandler(NumberFormatException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Number Format Exception", "요청의 값이 잘못됨");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponseForm> noSuchElementExceptionHandler(NoSuchElementException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("No Such Element Exception", "일치하는 요소가 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ApiErrorResponseForm> tokenInvalidExceptionHandler(TokenInvalidException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Token Invalid Exception", "토큰이 잘못 됨");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    public ResponseEntity<ApiErrorResponseForm> activityNotFoundExceptionHandler(ActivityNotFoundException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Activity Not Found Exception", "일정이 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonExistIdOrPasswordException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistIdOrPasswordExceptionHandler(NonExistIdOrPasswordException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist ID or Password Exception", "아이디 또는 비밀번호가 존재하지 않음");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistActivityException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistActivityExceptionHandler(NonExistActivityException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist Activity Exception", "Activity 가 club 또는 self-study 가 아님");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistFloorException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistFloorExceptionHandler(NonExistFloorException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist Floor Exception", "floor 가 1, 2, 3, 4가 아님");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistFloorOrPriorityException.class)
    public ResponseEntity<ApiErrorResponseForm> nonExistFloorOrPriorityExceptionHandler(NonExistFloorOrPriorityException ex) {
        ApiErrorResponseForm response = new ApiErrorResponseForm("Non Exist floor or priority Exception", "floor 또는 priority 가 숫자가 아님");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}