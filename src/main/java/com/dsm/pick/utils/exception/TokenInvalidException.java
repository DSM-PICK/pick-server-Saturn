package com.dsm.pick.utils.exception;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("토큰이 잘못되었습니다.");
    }
}
