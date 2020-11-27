package com.example.demo;

import com.example.demo.Utiliy.DSTemporalAdjuster;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class LocalDateTimeTest {
    @Test
    public void test_duration() {
        LocalDateTime here = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.of(2020, 11, 25, 13, 58, 30);
        System.out.println(Duration.between(before, here).getSeconds());
        System.out.println(Period.between(before.toLocalDate(), here.toLocalDate()).getDays());
    }

    @Test
    public void test_TemporalAdjusters() {
        LocalDate date1 = LocalDate.of(2020, 11, 25);
        //  이 주 혹은 그 다음주의 날을 return.
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime here = LocalDateTime.of(date2.getYear(), date2.getMonthValue(), date2.getDayOfMonth(), 0, 0, 0);
        System.out.println(here);
        System.out.println(date2);
        LocalDate date3 = date2.with(lastDayOfMonth());
        System.out.println(String.format("last day of %s is %s", date3.getMonth(), date3.getDayOfWeek()));
//        System.out.println(date3);
//        System.out.println(date3.with(lastDayOfYear()));
//        System.out.println(here.with(DSTemporalAdjuster.nextWorkingDay()));
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZoneId la = ZoneId.of("America/Los_Angeles");
        System.out.println(LocalDateTime.now().atZone(seoul));
        System.out.println(LocalDateTime.now().atZone(la));
        System.out.println(Instant.ofEpochMilli(System.currentTimeMillis()).getEpochSecond());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(here.format(formatter));

    }
}
