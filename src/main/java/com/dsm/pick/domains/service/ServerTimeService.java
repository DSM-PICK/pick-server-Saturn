package com.dsm.pick.domains.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ServerTimeService {

    public String getMonthAndDate() {

        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int intMonth = date.getMonth().getValue();
        int intDayOfMonth = date.getDayOfMonth();

        String month;
        String dayOfMonth;

        if(intMonth < 10) {
            month = "0" + String.valueOf(intMonth);
        } else {
            month = String.valueOf(intMonth);
        }

        if(intDayOfMonth < 10) {
            dayOfMonth = "0" + String.valueOf(intDayOfMonth);
        } else {
            dayOfMonth = String.valueOf(intDayOfMonth);
        }

        return month + dayOfMonth;
    }

    public String getDayOfWeek() {
        String dayOfWeeks[] = {"월", "화", "수", "목", "금", "토", "일"};
        LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        int intDayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeeks[intDayOfWeek - 1];
    }
}
