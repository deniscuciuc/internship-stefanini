package org.dcuciuc.course.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.course.Course;
import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.course.impl.mappers.CourseMapper;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.dao.impl.AbstractDAOImpl;
import org.dcuciuc.dto.filtres.UserSearchDTO;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class CourseDAOImpl extends AbstractDAOImpl<Course> implements CourseDAO<Course> {

    private static final Logger logger = LogManager.getLogger(CourseDAOImpl.class);

    public CourseDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Course create(Course course) {
        String sql = " INSERT INTO courses (course_name) VALUES ( ? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(con -> {
                    PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, course.getName());
                    return stmt;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public Course update(Course course, Long id) {
        String sql = " UPDATE courses SET course_name = ? WHERE id = ? ";
        getJdbcTemplate().update(sql, course.getName(), id);
        return getById(id);
    }

    @Override
    public Long removeById(Long id) throws EntityNotFoundException {
        String sql = " DELETE FROM courses WHERE id = ? ";
        getJdbcTemplate().update(sql, id);
        return id;
    }

    @Override
    public Course getById(Long id) throws EntityNotFoundException {
        String sql = " SELECT * FROM courses WHERE id = ? ";
        Course course = getJdbcTemplate().queryForObject(sql, new CourseMapper(), id);
        return course;
    }

    @Override
    public List<Course> findCoursesByUserId(UserSearchDTO userSearchDTO) {
        String sql = " SELECT DISTINCT courses.id, courses.course_name " +
                " FROM courses " +
                " JOIN users_courses_grades ucg on courses.id = ucg.course_id " +
                " JOIN courses c on c.id = ucg.course_id " +
                " WHERE ucg.user_id = ? " +
                " LIMIT ? , ?";

        List<Course> courses = getJdbcTemplate()
                .query(sql,
                        new CourseMapper(),
                        userSearchDTO.getSearchByIdValue(),
                        getOffset(userSearchDTO.getCurrentPage(), userSearchDTO.getPageSize()),
                        userSearchDTO.getPageSize()
                );

        return courses;
    }

    @Override
    public List<Course> findAllCoursesByUserId(Long userId) {
        String sql = " SELECT DISTINCT courses.id, courses.course_name " +
                " FROM courses " +
                " JOIN users_courses_grades ucg on courses.id = ucg.course_id " +
                " JOIN courses c on c.id = ucg.course_id " +
                " WHERE ucg.user_id = ? ";

        List<Course> courses = getJdbcTemplate()
                .query(sql,
                        new CourseMapper(),
                        userId
                );

        return courses;
    }

    @Override
    public void assignCourseToUser(Long courseId, Long userId, Long gradeId) {
        String sql = " INSERT INTO users_courses_grades (course_id, user_id, grade_id) VALUES ( ? , ? , ? ) ";
        getJdbcTemplate().update(sql, courseId, userId, gradeId);
    }

    @Override
    public void removeUserFromCourse(Long courseId, Long userId, Long gradeId) {
        String sql = " DELETE FROM users_courses_grades " +
                " WHERE course_id = ?" +
                " AND user_id = ? " +
                " AND grade_id = ? ";
        getJdbcTemplate().update(sql, courseId, userId, gradeId);
    }

    @Override
    public void removeUserFromCourses(Long userId) {
        String sql = " DELETE FROM users_courses_grades WHERE user_id = ? ";
        getJdbcTemplate().update(sql, userId);
    }
}
