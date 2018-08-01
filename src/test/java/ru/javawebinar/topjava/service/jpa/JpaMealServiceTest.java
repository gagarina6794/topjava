package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.JPA)
public class JpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUserUnsupported() {
        thrown.expect(UnsupportedOperationException.class);
        service.getWithUser(MEAL1_ID, USER_ID);
    }
}
