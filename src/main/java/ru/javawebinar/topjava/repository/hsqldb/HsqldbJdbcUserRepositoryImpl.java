package ru.javawebinar.topjava.repository.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcUserRepositoryImpl;

@Repository
public class HsqldbJdbcUserRepositoryImpl extends AbstractJdbcUserRepositoryImpl {

    @Autowired
    public HsqldbJdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
}
