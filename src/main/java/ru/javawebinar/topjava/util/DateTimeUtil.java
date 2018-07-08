package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDateTime(MealWithExceed meal, LocalDate dateBegin, LocalDate dateEnd, LocalTime timeBegin, LocalTime timeEnd) {
        return meal.getDateTime().toLocalDate().compareTo(dateBegin) >= 0 && meal.getDateTime().toLocalDate().compareTo(dateEnd) <= 0
                && meal.getDateTime().toLocalTime().compareTo(timeBegin) >= 0 && meal.getDateTime().toLocalTime().compareTo(timeEnd) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
