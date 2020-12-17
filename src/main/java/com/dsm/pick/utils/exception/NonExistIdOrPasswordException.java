package com.dsm.pick.utils.exception;

public class NonExistIdOrPasswordException extends RuntimeException {
    public NonExistIdOrPasswordException() {
        super("아이디 또는 비밀번호가 존재하지 않습니다.");
    }
}
