package com.dsm.pick.utils.exception;

public class ClubNotFoundException extends RuntimeException {
    public ClubNotFoundException() {
        super("동아리를 찾을 수 없습니다.");
    }
}
