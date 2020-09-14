package com.dsm.pick.utils.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException() {
        super("해당 날짜의 Activity를 찾을 수 없습니다.");
    }
}
