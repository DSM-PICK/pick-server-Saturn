package com.dsm.pick.utils.exception;

public class NonExistEncodingOrCryptographicAlgorithmException extends RuntimeException {
    public NonExistEncodingOrCryptographicAlgorithmException() {
        super("현재 인코딩 방식 또는 암호화 알고리즘이 잘못되었습니다.");
    }
}
