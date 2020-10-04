package com.example.demo;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class LocalDateTimeTest {
    @Test
    public void testDateTimeFormatter() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                // 없을 수도 있다면 [ ] 로 감싸야 한다.
                .appendPattern("dd/MM/yyyy[ ][HH][:mm][:ss]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 10)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 1)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 10)
                .toFormatter();
        LocalDateTime now = LocalDateTime.now().plusDays(10L);
        String nowStr = now.toString();
        //  System.out.println(now.format(formatter));
        // System.out.println(LocalDateTime.parse(nowStr,DateTimeFormatter.ofPattern("MM/d/yyyy")));
        // LocalDateTime.parse(str, formatter);는 String str를 formatter처럼 parsing해서 LocalDateTime object로 만들겠다는 뜻이다.
        //
        System.out.println(LocalDateTime.parse("12/12/2014 12:12:12", formatter));
        System.out.println(LocalDateTime.parse("12/12/2014", formatter));
        System.out.println(LocalDateTime.parse("2014-12-12T12:12:12").format( formatter));
        System.out.println(LocalDate.parse("2014-12-12").format( formatter));
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .appendPattern("[yyyy-]MM-dd")
                .parseDefaulting(ChronoField.YEAR, 2017)
                .toFormatter();
        System.out.println(LocalDate.parse("10-10", fmt));
        System.out.println(LocalDate.parse("2017-10-10", fmt));
    }
}
