package org.dcuciuc.user.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.dao.impl.AbstractDAOImpl;
import org.dcuciuc.dto.filtres.UserSearchDTO;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.dcuciuc.user.enums.UserRole;
import org.dcuciuc.user.impl.mappers.AllValuesUserMapper;
import org.dcuciuc.user.impl.mappers.NoPasswordUserMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO<User> {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    public UserDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public User create(User user) {
        String sql = " INSERT INTO users (email, password, role, is_active, profile_id) VALUES ( ? , ? , ? , ? , ? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(con -> {
                    PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, user.getEmail());
                    stmt.setString(2, user.getPassword());
                    stmt.setString(3, user.getRole().toString());
                    stmt.setBoolean(4, user.isActive());
                    stmt.setLong(5, user.getProfile().getId());
                    return stmt;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public User update(User user, Long id) throws EntityNotFoundException {
        String sql = " UPDATE users SET email = ? , password = ? , role = ? WHERE id = ? ";
        getJdbcTemplate().update(sql, user.getEmail(), user.getPassword(), user.getRole().toString(), user.getId());
        return getById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = " DELETE FROM users WHERE id = ? ";
        getJdbcTemplate().update(sql, id);
        return id;
    }

    @Override
    public User getById(Long id) throws EntityNotFoundException {
        String sql = " SELECT * FROM users WHERE id = ? ";
        User user = getJdbcTemplate().queryForObject(sql, new NoPasswordUserMapper(), id);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws EntityNotFoundException {
        String sql = " SELECT * FROM users WHERE email = ? ";
        User user = getJdbcTemplate().queryForObject(sql, new AllValuesUserMapper(), email);
        return user;
    }

    @Override
    public List<User> getUsersByRoleAndCourseId(UserSearchDTO userSearchDTO) throws EntityNotFoundException {
        String sql = " SELECT DISTINCT users.id, users.email, users.is_active, users.profile_id, users.role " +
                " FROM users " +
                " JOIN users_courses_grades ucg ON users.id = ucg.user_id " +
                " JOIN courses c ON ucg.course_id = c.id " +
                " WHERE users.role = ? " +
                " AND course_id = ? " +
                " LIMIT ? , ? ";

        List<User> users = getJdbcTemplate()
                .query(
                        sql,
                        new NoPasswordUserMapper(),
                        userSearchDTO.getUserRole().toString(),
                        userSearchDTO.getSearchByIdValue(),
                        getOffset(userSearchDTO.getCurrentPage(), userSearchDTO.getPageSize()),
                        userSearchDTO.getPageSize()
                );

        return users;
    }

    @Override
    public List<User> findUsersByRole(UserSearchDTO userSearchDTO) {
        String sql = " SELECT * FROM users WHERE role = ? LIMIT ? , ? ";

        List<User> users = getJdbcTemplate()
                .query(
                        sql,
                        new NoPasswordUserMapper(),
                        userSearchDTO.getUserRole().toString(),
                        getOffset(userSearchDTO.getCurrentPage(), userSearchDTO.getPageSize()),
                        userSearchDTO.getPageSize()
                );

        return users;
    }

    @Override
    public List<User> findAllUsersByCourseIdGradeIdAndRole(Long courseId, Long gradeId, UserRole role) throws EntityNotFoundException {
        String sql = " SELECT DISTINCT users.id, users.email, users.is_active, users.profile_id, users.role " +
                " FROM users " +
                " JOIN users_courses_grades ucg on users.id = ucg.user_id" +
                " JOIN courses c on c.id = ucg.course_id " +
                " JOin grades g on ucg.grade_id = g.id" +
                " WHERE course_id = ? " +
                " AND grade_id = ? " +
                " AND users.role = ? ";

        List<User> users = getJdbcTemplate()
                .query(
                        sql,
                        new NoPasswordUserMapper(),
                        courseId,
                        gradeId,
                        role.toString()
                );

        return users;
    }

    @Override
    public List<User> findAllStudentsByGradeId(Long gradeId) throws EntityNotFoundException {
        String sql = " SELECT DISTINCT users.id, users.email, users.is_active, users.profile_id, users.role " +
                " FROM users " +
                " JOIN students_grades sg on users.id = sg.student_id" +
                " JOIN grades g on g.id = sg.grade_id" +
                " WHERE grade_id = ? ";

        List<User> students = getJdbcTemplate().query(sql, new NoPasswordUserMapper(), gradeId);

        return students;
    }

    @Override
    public List<User> findAllParentsByStudentId(Long studentId) throws EntityNotFoundException {
        String sql = " SELECT DISTINCT users.id, users.email, users.is_active, users.profile_id, users.role " +
                " FROM users " +
                " JOIN parents_students ps on users.id = ps.parent_id" +
                " WHERE student_id = ? " +
                " AND NOT users.id = ? ";

        List<User> parents = getJdbcTemplate().query(sql, new NoPasswordUserMapper(), studentId, studentId);
        return parents;
    }

    @Override
    public Integer countByRole(UserRole role) {
        String sql = " SELECT COUNT(*) FROM users WHERE users.role = ?";
        Integer total = getJdbcTemplate().queryForObject(sql, Integer.class, role.toString());
        return total;
    }

    @Override
    public Integer countByRoleAndCourseId(UserRole role, Long courseId) {
        String sql = " SELECT DISTINCT COUNT(*) " +
                " FROM users " +
                " JOIN users_courses_grades ucg on users.id = ucg.user_id " +
                " JOIN courses c on c.id = ucg.course_id " +
                " WHERE role = ? " +
                " AND " +
                " ucg.course_id = ? ";
        Integer total = getJdbcTemplate().queryForObject(sql, Integer.class, role.toString(), courseId);
        return total;
    }

    @Override
    public List<User> findAllStudentsByParentId(Long parentId) {
        String sql = " SELECT DISTINCT users.id, users.email, users.is_active, users.profile_id, users.role " +
                " FROM users " +
                " JOIN parents_students ps on users.id = ps.student_id" +
                " WHERE parent_id = ? ";

        List<User> parents = getJdbcTemplate().query(sql, new NoPasswordUserMapper(), parentId);
        return parents;
    }

    @Override
    public void assignParentToStudent(Long parentId, Long studentId) {
        String sql = " INSERT INTO parents_students(parent_id, student_id) VALUES ( ? , ? )";
        getJdbcTemplate().update(sql, parentId, studentId);
    }
}
