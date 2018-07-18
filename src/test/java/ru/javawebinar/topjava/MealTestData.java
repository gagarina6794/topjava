package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID_1 = START_SEQ + 2;
    public static final int USER_MEAL_ID_2 = START_SEQ + 3;
    public static final int USER_MEAL_ID_3 = START_SEQ + 4;
    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 5;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 6;
    public static final int ADMIN_MEAL_ID_3 = START_SEQ + 7;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1,
            LocalDateTime.of(2018, 7, 15, 9, 36), "Завтрак Пользователя", 500);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2,
            LocalDateTime.of(2018, 7, 16, 12, 36), "Обед Пользователя", 1000);
    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3,
            LocalDateTime.of(2018, 7, 17, 17, 36), "Ужин Пользователя", 300);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1,
            LocalDateTime.of(2018, 7, 17, 9, 36), "Завтрак Админа", 150);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2,
            LocalDateTime.of(2018, 7, 17, 12, 36), "Обед Админа", 200);
    public static final Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID_3,
            LocalDateTime.of(2018, 7, 17, 17, 36), "Ужин Админа", 130);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
