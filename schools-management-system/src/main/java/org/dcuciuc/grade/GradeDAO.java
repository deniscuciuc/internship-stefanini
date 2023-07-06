package org.dcuciuc.grade;

import org.dcuciuc.dao.AbstractDAO;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;

/**
 * GradeDAO responds for Grade entity
 *
 * @param <Grade>
 * @author dcuciuc
 */
public interface GradeDAO<Grade> extends AbstractDAO<Grade> {

    void assignGradeToStudent(Long gradeId, Long studentId);

    Grade findGradeOfStudentByYears(Long studentId, int yearFrom, int yearTo) throws EntityNotFoundException;

    void removeGradeFromStudent(Long gradeId, Long studentId);

    void removeGradesFromStudent(Long studentId);
}
