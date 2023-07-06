package org.dcuciuc.user;

import org.dcuciuc.dto.PageResponse;
import org.dcuciuc.dto.ParentDTO;
import org.dcuciuc.dto.StudentDTO;
import org.dcuciuc.dto.TeacherDTO;
import org.dcuciuc.dto.auth.RegistrationDTO;
import org.dcuciuc.user.enums.UserRole;
import java.util.List;

/**
 * Service is responsible for operations with user entity.
 * Here is methods for fetching users by roles (Parent, Student and Employee) and another data
 *
 * @author dcuciuc
 */
public interface UserService {

    User registerUser(RegistrationDTO registrationDTO);

    User getUserById(Long id);

    User getUserByEmail(String email);

    PageResponse<User> getUsersByRole(UserRole role, Integer currentPage, Integer pageSize);

    TeacherDTO getTeacherById(Long teacherId);

    PageResponse<StudentDTO> getStudentsByCourseId(Long courseId, Integer currentPage, Integer pageSize);

    List<StudentDTO> getAllStudentsByCourseIdAndGradeId(Long courseId, Long gradeId);

    ParentDTO getParentById(Long parentId);

    ParentDTO assignParentToStudent(Long parentId, Long studentId);
}
