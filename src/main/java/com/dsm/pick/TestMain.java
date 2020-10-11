package com.dsm.pick;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestMain {
    public static void main(String[] args) {

        System.out.println(LocalDate.of(2020, 10, 4).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 5).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 6).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 7).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 8).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 9).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 10).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 11).getDayOfWeek().getValue());
        System.out.println(LocalDate.of(2020, 10, 12).getDayOfWeek().getValue());
    }
}