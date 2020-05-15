package com.example.validator_demo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        int add = test.add();
        System.out.println("value:" + add);
        LocalDate now = LocalDate.now();
        Thread.sleep(200);
        LocalDate end = LocalDate.of(2020, 6, 1);
        boolean after = now.isAfter(end);
        long until = now.until(end, ChronoUnit.MONTHS);
        System.out.println(after);
        System.out.println("until:" + until);
    }

    public int add() {
        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.now();
        date.isAfter(date1);
        int a = 20;
        int b = 4;
        int c = (a + b) / 2;
        return c;
    }
}
