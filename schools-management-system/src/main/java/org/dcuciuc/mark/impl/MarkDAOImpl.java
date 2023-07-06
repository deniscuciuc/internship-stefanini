package org.dcuciuc.mark.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.dao.impl.AbstractDAOImpl;
import org.dcuciuc.dto.filtres.MarkFilterDTO;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.MarkDAO;
import org.dcuciuc.mark.impl.mappers.MarkMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MarkDAOImpl extends AbstractDAOImpl<Mark> implements MarkDAO<Mark> {

    private static final Logger logger = LogManager.getLogger(MarkDAOImpl.class);

    public MarkDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Mark create(Mark mark) {
        String sql = " INSERT INTO marks (mark, created_at) VALUES ( ? , ? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, mark.getMark().toString());
            stmt.setDate(2, Date.valueOf(mark.getCreatedAt()));
            return stmt;
        }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public Mark update(Mark mark, Long id) {
        String sql = " UPDATE marks SET mark = ?, created_at = ? WHERE id = ? ";
        getJdbcTemplate().update(sql, mark.getMark().toString(), Date.valueOf(mark.getCreatedAt()), id);
        return getById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = " DELETE FROM marks WHERE id = ? ";
        getJdbcTemplate().update(sql, id);
        return id;
    }

    @Override
    public Mark getById(Long id) {
        String sql = " SELECT * FROM marks WHERE id = ? ";
        Mark mark = getJdbcTemplate().queryForObject(sql, new MarkMapper(), id);
        return mark;
    }

    @Override
    public Mark createMark(Mark mark, Long studentId, Long teacherId, Long courseId) {
        String sql = " INSERT INTO marks (student_id, course_id, teacher_id, mark, created_at) " +
                " VALUES ( ? , ? , ? , ? , ? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, studentId);
            stmt.setLong(2, courseId);
            stmt.setLong(3, teacherId);
            stmt.setString(4, mark.getMark().toString());
            stmt.setDate(5, Date.valueOf(mark.getCreatedAt()));
            return stmt;
        }, keyHolder);

        return getById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Mark> findMarksByStudentAndCourseIdFiltered(MarkFilterDTO markFilterDTO) {
        String sql = " SELECT * FROM marks " +
                " WHERE student_id = ? " +
                " AND course_id = ? " +
                " LIMIT ? , ? ";

        List<Mark> foundMarks = getJdbcTemplate()
                .query(
                        sql,
                        new MarkMapper(),
                        markFilterDTO.getStudentId(),
                        markFilterDTO.getCourseId(),
                        getOffset(markFilterDTO.getCurrentPage(), markFilterDTO.getPageSize()),
                        markFilterDTO.getPageSize()
                );

        return foundMarks;
    }

    @Override
    public List<Mark> findAllMarksByIdOfStudentAndCourseAndTeacherAndIntervalOfDates(MarkFilterDTO markFilterDTO) {
        String sql = " SELECT * FROM marks " +
                " WHERE student_id = ? " +
                " AND course_id = ? " +
                " AND teacher_id = ? " +
                " AND created_at BETWEEN ? AND ? ";

        List<Mark> marks = getJdbcTemplate()
                .query(
                        sql,
                        new MarkMapper(),
                        markFilterDTO.getStudentId(),
                        markFilterDTO.getCourseId(),
                        markFilterDTO.getTeacherId(),
                        markFilterDTO.getDateFrom(),
                        markFilterDTO.getDateTo()
                );

        return marks;
    }
}
