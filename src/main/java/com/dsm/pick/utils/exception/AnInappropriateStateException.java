package com.dsm.pick.utils.exception;

public class AnInappropriateStateException extends RuntimeException {
    public AnInappropriateStateException() {
        super("요청으로 들어온 학생의 상태가 적절하지 않습니다.");
    }
}
