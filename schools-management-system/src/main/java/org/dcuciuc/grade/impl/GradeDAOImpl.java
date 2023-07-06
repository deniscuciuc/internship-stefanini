package org.dcuciuc.grade.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.dao.impl.AbstractDAOImpl;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
import org.dcuciuc.grade.impl.mappers.GradeMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class GradeDAOImpl extends AbstractDAOImpl<Grade> implements GradeDAO<Grade> {

    private static final Logger logger = LogManager.getLogger(GradeDAOImpl.class);

    public GradeDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Grade create(Grade grade) {
        String sql = " INSERT INTO grades (year_from, year_to, grade_number, subgroup) " +
                " VALUES ( ? , ? , ? , ?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate()
                .update(con -> {
                    PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    stmt.setInt(1, grade.getYearFrom());
                    stmt.setInt(2, grade.getYearTo());
                    stmt.setInt(3, grade.getNumber());
                    stmt.setString(4, grade.getSubgroup().toString());
                    return stmt;
                }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public Grade update(Grade grade, Long id) {
        String sql = " UPDATE grades " +
                " SET grade_number = ? , subgroup = ? , year_from = ? , year_to = ? " +
                " WHERE id = ? ";

        getJdbcTemplate()
                .update(
                        sql,
                        grade.getNumber(), grade.getSubgroup().toString(), grade.getYearFrom(), grade.getYearTo(), id
                );

        return getById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = " DELETE FROM grades WHERE id = ? ";
        getJdbcTemplate().update(sql, id);
        return id;
    }

    @Override
    public Grade getById(Long id) {
        String sql = " SELECT * FROM grades WHERE id = ? ";
        Grade grade = getJdbcTemplate().queryForObject(sql, new GradeMapper(), id);
        return grade;
    }

    @Override
    public void assignGradeToStudent(Long gradeId, Long studentId) {
        String sql = " INSERT INTO students_grades (student_id, grade_id) VALUES ( ? , ? ) ";
        getJdbcTemplate().update(sql, studentId, gradeId);
    }

    @Override
    public Grade findGradeOfStudentByYears(Long studentId, int yearFrom, int yearTo) {
        String sql = " SELECT grades.id, grades.year_from, grades.year_to, grades.grade_number, grades.subgroup " +
                " FROM grades " +
                " JOIN students_grades sg on grades.id = sg.grade_id " +
                " JOIN users u on u.id = sg.student_id " +
                " WHERE student_id = ? " +
                " AND grades.year_from = ? " +
                " AND grades.year_to = ? ";

        Grade grade = getJdbcTemplate().queryForObject(sql, new GradeMapper(), studentId, yearFrom, yearTo);
        return grade;
    }

    @Override
    public void removeGradeFromStudent(Long gradeId, Long studentId) {
        String sql = " DELETE FROM students_grades " +
                " WHERE student_id = ? " +
                " AND grade_id = ? ";
        getJdbcTemplate().update(sql, studentId, gradeId);
    }

    @Override
    public void removeGradesFromStudent(Long studentId) {
        String sql = " DELETE FROM students_grades WHERE student_id = ? ";
        getJdbcTemplate().update(sql, studentId);
    }
}
