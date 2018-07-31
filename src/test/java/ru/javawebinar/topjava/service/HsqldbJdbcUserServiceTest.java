package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.profile.resolver.HsqldbJdbcProfileResolver;

@ActiveProfiles(resolver = HsqldbJdbcProfileResolver.class)
public class HsqldbJdbcUserServiceTest extends AbstractUserServiceTest {
}
