package org.dcuciuc.profile.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.dao.impl.AbstractDAOImpl;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.ProfileDAO;
import org.dcuciuc.profile.impl.mappers.ProfileMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class ProfileDAOImpl extends AbstractDAOImpl<Profile> implements ProfileDAO<Profile> {

    private static final Logger logger = LogManager.getLogger(ProfileDAOImpl.class);

    public ProfileDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Profile create(Profile profile) {
        String sql = " INSERT INTO profiles (first_name, last_name, gender, phone_number, address) " +
                " VALUES ( ? , ? , ? , ? , ? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, profile.getFirstName());
            stmt.setString(2, profile.getLastName());
            stmt.setString(3, profile.getGender().toString());
            stmt.setString(4, profile.getPhoneNumber());
            stmt.setString(5, profile.getAddress());
            return stmt;
        }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public Profile update(Profile profile, Long id) {
        String sql = " UPDATE profiles SET first_name = ? , last_name = ? , gender = ? , " +
                " phone_number = ? , address = ? WHERE id = ? ";

        getJdbcTemplate()
                .update(
                        sql,
                        profile.getFirstName(),
                        profile.getLastName(),
                        profile.getGender().toString(),
                        profile.getPhoneNumber(),
                        profile.getAddress(),
                        id
                );

        return getById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = " DELETE FROM profiles WHERE id = ? ";
        getJdbcTemplate().update(sql, id);
        return id;
    }

    @Override
    public Profile getById(Long id) {
        String sql = " SELECT * FROM profiles WHERE id = ? ";
        Profile profile = getJdbcTemplate().queryForObject(sql, new ProfileMapper(), id);
        return profile;
    }
}
