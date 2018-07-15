package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL_1 = new Meal(1,
            LocalDateTime.of(2018, 07, 16, 9, 36), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(2,
            LocalDateTime.of(2018, 07, 16, 12, 36), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(3,
            LocalDateTime.of(2018, 07, 16, 17, 36), "Ужин", 300);
    public static final Meal MEAL_4 = new Meal(4,
            LocalDateTime.of(2018, 07, 17, 9, 36), "Завтрак", 150);
    public static final Meal MEAL_5 = new Meal(5,
            LocalDateTime.of(2018, 07, 17, 12, 36), "Обед", 200);
    public static final Meal MEAL_6 = new Meal(6,
            LocalDateTime.of(2018, 07, 17, 17, 36), "Ужин", 130);

//    public static void assertMatch(Meal actual, Meal expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
//    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
