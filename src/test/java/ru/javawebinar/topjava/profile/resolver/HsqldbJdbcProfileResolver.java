package ru.javawebinar.topjava.profile.resolver;

import org.springframework.test.context.ActiveProfilesResolver;
import ru.javawebinar.topjava.Profiles;

public class HsqldbJdbcProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> testClass) {
        return new String[]{Profiles.HSQLDB_JPA};
    }
}
