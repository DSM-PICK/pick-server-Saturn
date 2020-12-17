package com.dsm.pick.utils.exception;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException() {
        super("토큰이 만료 되었습니다.");
    }
}
