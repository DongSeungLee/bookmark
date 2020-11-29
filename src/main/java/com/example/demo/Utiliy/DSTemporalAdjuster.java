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
                    // dayOfWeek는 enum이다. MON,~, SUN까지 enum으로
                    // data를 표시할 수 있다.
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int dateAdd = 1;
                    if (dow == DayOfWeek.FRIDAY) dateAdd = 3;
                    else if (dow == DayOfWeek.SATURDAY) dateAdd = 2;
                    return temporal.plusDays(dateAdd);
                    // plus를 보면 뒤의 paramater로 ChronoUnit.DAYS가 오면 plusDays로
                    // 되어 있다. 따라서 temporal.plusDays(dateAdd);로 해도 상관없다.
                    //return temporal.plus(dateAdd, ChronoUnit.DAYS);
                }
        );
    }
}
