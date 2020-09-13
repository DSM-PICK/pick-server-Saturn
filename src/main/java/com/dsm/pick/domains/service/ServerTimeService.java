package com.dsm.pick.domains.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServerTimeService {

    public String getMonthAndDate() {
        LocalDateTime date = LocalDateTime.now();
        int intDayOfMonth = date.getDayOfMonth();
        int intMonth = date.getMonth().getValue();

        String dayOfMonth;
        String month;

        if(intMonth < 10) {
            dayOfMonth = "0" + String.valueOf(intMonth);
        } else {
            dayOfMonth = String.valueOf(intMonth);
        }

        if(intDayOfMonth < 10) {
            month = "0" + String.valueOf(intDayOfMonth);
        } else {
            month = String.valueOf(intDayOfMonth);
        }

        return month + dayOfMonth;
    }
}
