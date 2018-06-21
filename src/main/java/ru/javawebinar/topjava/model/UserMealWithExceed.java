package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserMealWithExceed implements Comparable<UserMealWithExceed> {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMealWithExceed that = (UserMealWithExceed) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public String toString() {
        return "UserMealWithExceed:   " +
                "dateTime = " + dateTime +
                " |   description = '" + description + '\'' +
                " |   calories = " + calories +
                " |   exceed = " + exceed +
                '|';
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }

    @Override
    public int compareTo(UserMealWithExceed o) {
        return dateTime.compareTo(o.dateTime);
    }
}
