package com.dsm.pick.utils.exception;

public class TokenInvalid extends RuntimeException {
    public TokenInvalid() {
        super("토큰이 잘못되었습니다.");
    }
}
