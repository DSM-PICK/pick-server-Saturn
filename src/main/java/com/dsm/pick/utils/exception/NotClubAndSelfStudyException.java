package com.dsm.pick.utils.exception;

public class NotClubAndSelfStudyException extends RuntimeException {

    public NotClubAndSelfStudyException() {
        super("schedule 이 club 또는 self-study 가 아닙니다.");
    }

    public NotClubAndSelfStudyException(String message) {
        super(message);
    }
}
