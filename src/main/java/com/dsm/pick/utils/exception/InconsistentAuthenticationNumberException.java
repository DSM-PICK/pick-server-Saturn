package com.dsm.pick.utils.exception;

public class InconsistentAuthenticationNumberException extends RuntimeException {
    public InconsistentAuthenticationNumberException() {
        super("인증 번호가 일치하지 않습니다.");
    }
}
