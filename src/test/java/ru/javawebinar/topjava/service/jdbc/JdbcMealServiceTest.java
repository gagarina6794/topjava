package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUserUnsupported() {
        thrown.expect(UnsupportedOperationException.class);
        service.getWithUser(MEAL1_ID, USER_ID);
    }
}
