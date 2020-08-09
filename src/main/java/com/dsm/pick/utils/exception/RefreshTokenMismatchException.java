package com.dsm.pick.utils.exception;

public class RefreshTokenMismatchException extends RuntimeException {
    public RefreshTokenMismatchException() {
        super("요청한 Refresh Token과 일치하지 않습니다.");
    }
}