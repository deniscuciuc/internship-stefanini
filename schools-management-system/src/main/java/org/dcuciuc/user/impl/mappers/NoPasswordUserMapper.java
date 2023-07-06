package org.dcuciuc.user.impl.mappers;

import org.dcuciuc.profile.Profile;
import org.dcuciuc.user.User;
import org.dcuciuc.user.enums.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoPasswordUserMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("email"),
                UserRole.valueOf(rs.getString("role")),
                new Profile(rs.getLong("profile_id")),
                rs.getBoolean("is_active")
        );
    }

}
