package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateRepository {

    private Map<String, LocalDate> dateMap = new ConcurrentHashMap<>();
    private Map<String, LocalTime> timeMap = new ConcurrentHashMap<>();

    public void setDate(String key, LocalDate date) {
        dateMap.put(key, date);
    }

    public void setTime(String key, LocalTime time) {
        timeMap.put(key, time);
    }

    public void removeDate(String key) {
        dateMap.remove(key);
    }

    public void removeTime(String key) {
        timeMap.remove(key);
    }

    public LocalDate getDate(String date) {
        return dateMap.get(date);
    }

    public LocalTime getTime(String time) {
        return timeMap.get(time);
    }
}
