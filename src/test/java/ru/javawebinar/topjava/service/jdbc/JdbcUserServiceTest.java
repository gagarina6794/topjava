package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMealUnsupported() {
        thrown.expect(UnsupportedOperationException.class);
        service.getWithMeal(USER_ID);
    }
}
