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
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        //  이 주 혹은 그 다음주의 같은 요일의 날을 return.
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime here = LocalDateTime.of(date2.getYear(), date2.getMonthValue(), date2.getDayOfMonth(), 0, 0, 0);
        System.out.println(here);
        System.out.println(date2);
        // LocalDate에서의 해당 달의 마지막 날, LocalDate를 return한다.
        LocalDate date3 = date2.with(lastDayOfMonth());
        System.out.println(String.format("last day of %s is %s", date3.getMonth(), date3.getDayOfWeek()));
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZoneId la = ZoneId.of("America/Los_Angeles");
        // 현재 구한 시간의 시간대를 한국의 Seoul로 결정
        System.out.println(LocalDateTime.now().atZone(seoul));
        // 현재 구한 시간의 시간대를 LA의 시간으로 결정, 이렇게 해야 DST(Daylight Saving Time) = summer time과 같은 복잡한 시간도 처리해줄 수 있다.
        System.out.println(LocalDateTime.now().atZone(la));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(here.format(formatter));
        Instant instant = Instant.now();
        System.out.println(LocalDateTime.ofInstant(instant, la));
    }

    @Test
    public void test_DSTemporalAdjuster() {
        LocalDate here = LocalDate.of(2020, 11, 27);
        System.out.println(here.with(DSTemporalAdjuster.nextWorkingDay()));
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime firstDayOfStartDateTime = LocalDateTime.of(start.getYear(), start.getMonthValue(), 1, 0, 0, 0);
        System.out.println("firstr day of month " + start.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0));
        System.out.println("first day of month : " + firstDayOfStartDateTime);
        System.out.println("last day of month : " + firstDayOfStartDateTime.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("next monday is :" + start.plusDays(7L).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
    }

    @Test
    public void test_Instant() {
        System.out.println(System.currentTimeMillis());
        Period sevenDays = Period.ofDays(7);
        System.out.println(LocalDateTime.now().plusDays(sevenDays.getDays()));
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        list.removeIf(i -> i > 10);
    }

    @Test
    public void test_spring_version() {
        System.out.println(org.springframework.core.SpringVersion.getVersion());
        System.out.println(org.springframework.core.SpringVersion.getVersion());
    }
}
