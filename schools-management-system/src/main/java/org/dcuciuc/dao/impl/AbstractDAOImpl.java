package org.dcuciuc.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.dao.AbstractDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAOImpl<T> implements AbstractDAO<T> {
    private static final Logger logger = LogManager.getLogger(AbstractDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public AbstractDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected String wrapValueInPercentages(String str) {
        return "%" + str + "%";
    }

    protected Integer getOffset(int page, int pageSize) {
        return (page - 1) * pageSize;
    }
}
