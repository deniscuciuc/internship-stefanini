package org.dcuciuc.mark.impl.mappers;

import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.enums.MarkType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkMapper implements RowMapper<Mark> {


    @Override
    public Mark mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mark(
                rs.getLong("id"),
                MarkType.valueOf(rs.getString("mark")),
                rs.getDate("created_at").toLocalDate()
        );
    }
}
