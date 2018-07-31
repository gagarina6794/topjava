package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.profile.resolver.DataJpaProfileResolver;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(resolver = DataJpaProfileResolver.class)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getMealListTest() {
        List<Meal> mealList = service.getMealList(USER_ID);
        assertMatch(mealList, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}
