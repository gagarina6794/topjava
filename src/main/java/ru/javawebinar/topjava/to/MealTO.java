package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MealTO extends BaseTo {

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 2, max = 120, message = "value is not less than 2 and not more than 120 characters")
    private String description;

    @Range(min = 10, max = 5000, message = "value is not less than 10 and not more than 5000")
    private int calories;

    public MealTO() {
    }

    public MealTO(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "MealTO{" +
            "dateTime=" + dateTime +
            ", description='" + description + '\'' +
            ", calories=" + calories +
            ", id=" + id +
            '}';
    }
}
