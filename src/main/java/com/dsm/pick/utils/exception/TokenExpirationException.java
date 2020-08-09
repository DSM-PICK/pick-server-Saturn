package com.dsm.pick.utils.exception;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException() {
        super("토큰이 만료되었거나 잘못되었습니다.");
    }
}
