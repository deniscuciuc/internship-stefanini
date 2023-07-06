package org.dcuciuc.user;

import org.dcuciuc.dao.AbstractDAO;
import org.dcuciuc.dto.filtres.UserSearchDTO;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.user.enums.UserRole;

import java.util.List;

/**
 * This User DAO responds for all users.
 * Each user can have only one role so all method from this interface can be used to
 * execute operation above all types of users.
 *
 * @param <User>
 * @author dcuciuc
 */
public interface UserDAO<User> extends AbstractDAO<User> {


    /**
     * Finds user by email (searching is case-sensitive and email should be exact)
     *
     * @param email of user to be found
     * @return found user (with password)
     * @throws EntityNotFoundException if user with such email was not found
     */
    User findUserByEmail(String email) throws EntityNotFoundException;

    /**
     * Finds user by course name.
     * Searching is not case-sensitive so method can return all users with a similar value.
     * <br>
     * For example if you find "Bio" method will return users (by roles)
     * which have relationship with Biology course (if exists)
     *
     * @param userSearchDTO with values: currentPage, pageSize, userRole, searchValue
     * @return list of found users (with no passwords)
     * @throws EntityNotFoundException if users by such course name not found
     */
    List<User> getUsersByRoleAndCourseId(UserSearchDTO userSearchDTO) throws EntityNotFoundException;

    List<User> findUsersByRole(UserSearchDTO userSearchDTO);


    /**
     * Finds all users by course id and grade id
     *
     * @param courseId id of course
     * @param gradeId  id of grade
     * @param role     role of user
     * @return list of all users found by course id and grade id
     * @throws EntityNotFoundException if user not found by course id and grade id
     */
    List<User> findAllUsersByCourseIdGradeIdAndRole(Long courseId, Long gradeId, UserRole role) throws EntityNotFoundException;

    List<User> findAllStudentsByGradeId(Long gradeId) throws EntityNotFoundException;

    List<User> findAllParentsByStudentId(Long studentId) throws EntityNotFoundException;

    Integer countByRole(UserRole role);

    Integer countByRoleAndCourseId(UserRole role, Long courseId);

    List<User> findAllStudentsByParentId(Long parentId);

    void assignParentToStudent(Long parentId, Long studentId);
}
