package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.profile.resolver.DataJpaProfileResolver;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(resolver = DataJpaProfileResolver.class)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void getUserTest() {
        User user = service.getUser(MEAL1_ID, USER_ID);
        assertMatch(user, USER);
    }
}
