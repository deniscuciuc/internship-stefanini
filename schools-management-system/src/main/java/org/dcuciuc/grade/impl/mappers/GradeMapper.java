package org.dcuciuc.grade.impl.mappers;

import org.dcuciuc.grade.Grade;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeMapper implements RowMapper<Grade> {

    @Override
    public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Grade(
                rs.getLong("id"),
                rs.getInt("year_from"),
                rs.getInt("year_to"),
                rs.getInt("grade_number"),
                rs.getString("subgroup").charAt(0)
        );
    }
}
