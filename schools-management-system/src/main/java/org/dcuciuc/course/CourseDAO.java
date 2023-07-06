package org.dcuciuc.course;

import org.dcuciuc.dao.AbstractDAO;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.dto.filtres.UserSearchDTO;

import java.util.List;

/**
 * This Course DAO responds for the courses that students take and teachers teach
 *
 * @param <Course>
 * @author dcuciuc
 */
public interface CourseDAO<Course> extends AbstractDAO<Course> {

    /**
     * Finds courses of user by his id
     *
     * @param userSearchDTO with values: pageSize, currentPage, searchByIdValue
     * @return found list of courses
     * @throws EntityNotFoundException if courses not found by user id
     */
    List<Course> findCoursesByUserId(UserSearchDTO userSearchDTO) throws EntityNotFoundException;

    /**
     * Finds all courses by user id
     *
     * @param userId id of user courses to be found
     * @return list of courses found
     * @throws EntityNotFoundException if courses not found by user id
     */
    List<Course> findAllCoursesByUserId(Long userId) throws EntityNotFoundException;

    /**
     * Assigns user to course
     *
     * @param courseId id of course to be assigned to user
     * @param userId   id of user to be assigned to course
     * @param gradeId  id of grade to be assigned to course and user
     */
    void assignCourseToUser(Long courseId, Long userId, Long gradeId);

    void removeUserFromCourse(Long courseId, Long userId, Long gradeId);

    void removeUserFromCourses(Long userId);

}
