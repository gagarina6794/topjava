package ru.javawebinar.topjava.repository.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class HsqldbJdbcMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl<Timestamp> {

    @Autowired
    public HsqldbJdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp formatDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
