package org.dcuciuc.mark;

import org.dcuciuc.dao.AbstractDAO;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.dto.filtres.MarkFilterDTO;

import java.util.List;

/**
 * This Mark DAO is responsible for students marks (grades or absents)
 *
 * @param <Mark>
 * @author dcuciuc
 */
public interface MarkDAO<Mark> extends AbstractDAO<Mark> {

    /**
     * Creates mark with all references
     *
     * @param mark      mark to be created
     * @param studentId id of student to be assigned to mark
     * @param teacherId id of teacher to be assigned to mark
     * @param courseId  id of course to be assigned to mark
     * @return mark with id from database
     * @throws EntityNotFoundException if something went wrong while fetching back mark by id
     */
    Mark createMark(Mark mark, Long studentId, Long teacherId, Long courseId) throws EntityNotFoundException;

    /**
     * Finds marks by student and return it pageable
     *
     * @param markFilterDTO with all values
     * @return list of marks
     * @throws EntityNotFoundException if marks not found by user id
     */
    List<Mark> findMarksByStudentAndCourseIdFiltered(MarkFilterDTO markFilterDTO) throws EntityNotFoundException;

    /**
     * Finds marks by student id and interval of date and return them
     *
     * @param markFilterDTO
     * @return list of marks found by student id and interval of dates
     * @throws EntityNotFoundException if marks not found by student id and interval of dates
     */
    List<Mark> findAllMarksByIdOfStudentAndCourseAndTeacherAndIntervalOfDates(MarkFilterDTO markFilterDTO) throws EntityNotFoundException;
}
