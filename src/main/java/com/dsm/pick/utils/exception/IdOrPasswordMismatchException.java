package com.dsm.pick.utils.exception;

public class IdOrPasswordMismatchException extends RuntimeException {
    public IdOrPasswordMismatchException() {
        super("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}
