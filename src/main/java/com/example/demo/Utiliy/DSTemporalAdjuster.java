package com.example.demo.Utiliy;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DSTemporalAdjuster {
    public static TemporalAdjuster nextWorkingDay() {
        // parameter가 UnaryOperator<LocalDate>이다.
        return TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dateAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) dateAdd = 3;
                    else if (dow == DayOfWeek.SATURDAY) dateAdd = 2;
                    return temporal.plusDays(dateAdd);
                    //return temporal.plus(dateAdd, ChronoUnit.DAYS);
                }
        );
    }
}
