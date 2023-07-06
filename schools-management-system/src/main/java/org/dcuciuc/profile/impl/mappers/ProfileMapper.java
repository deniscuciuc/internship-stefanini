package org.dcuciuc.profile.impl.mappers;

import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.enums.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileMapper implements RowMapper<Profile> {


    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Profile(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("phone_number"),
                rs.getString("address")
        );
    }
}
