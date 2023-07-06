package org.dcuciuc.user.impl;

import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.dto.PageResponse;
import org.dcuciuc.dto.ParentDTO;
import org.dcuciuc.dto.TeacherDTO;
import org.dcuciuc.dto.auth.RegistrationDTO;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.dcuciuc.user.UserService;
import org.dcuciuc.user.enums.UserRole;
import org.dcuciuc.profile.ProfileDAO;
import org.dcuciuc.dto.StudentDTO;
import org.dcuciuc.dto.filtres.UserSearchDTO;
import org.dcuciuc.course.Course;
import org.dcuciuc.profile.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO<User> userDAO;
    private final ProfileDAO<Profile> profileDAO;
    private final CourseDAO<Course> courseDAO;
    private final GradeDAO<Grade> gradeDAO;

    public UserServiceImpl(UserDAO<User> userDAO, ProfileDAO<Profile> profileDAO, CourseDAO<Course> courseDAO,
                           GradeDAO<Grade> gradeDAO) {
        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.courseDAO = courseDAO;
        this.gradeDAO = gradeDAO;
    }


    @Override
    public User registerUser(RegistrationDTO registrationDTO) {
        Profile profile = new Profile(
                registrationDTO.getFirstName(),
                registrationDTO.getLastName(),
                registrationDTO.getGender(),
                registrationDTO.getPhoneNumber(),
                registrationDTO.getAddress()
        );
        profile = profileDAO.create(profile);

        User user = new User(
                registrationDTO.getEmail(),
                registrationDTO.getPassword(),
                registrationDTO.getRole(),
                profile,
                true
        );
        user = userDAO.create(user);
        user.setProfile(profile);

        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userDAO.getById(id);
        Profile profile = profileDAO.getById(user.getProfile().getId());
        user.setProfile(profile);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public PageResponse<User> getUsersByRole(UserRole role, Integer currentPage, Integer pageSize) {
        UserSearchDTO userSearchDTO = new UserSearchDTO(currentPage, pageSize, role);
        List<User> users = userDAO.findUsersByRole(userSearchDTO);

        for (User user : users) {
            Profile profile = profileDAO.getById(user.getProfile().getId());
            user.setProfile(profile);
        }

        Integer totalElements = userDAO.countByRole(role);
        Integer totalPages = getTotalPages(totalElements, pageSize);
        Boolean last = isLastPage(currentPage, totalPages);
        Boolean first = isFirstPage(currentPage);

        return new PageResponse<>(currentPage, pageSize, last, first, totalElements, totalPages, users);
    }

    @Override
    public TeacherDTO getTeacherById(Long teacherId) {
        User user = userDAO.getById(teacherId);
        Profile profile = profileDAO.getById(user.getProfile().getId());
        List<Course> courses = courseDAO.findAllCoursesByUserId(teacherId);

        return new TeacherDTO(
                teacherId,
                profile.getFirstName(),
                profile.getLastName(),
                user.getEmail(),
                profile.getPhoneNumber(),
                courses
        );
    }

    @Override
    public PageResponse<StudentDTO> getStudentsByCourseId(Long courseId, Integer currentPage, Integer pageSize) {
        UserSearchDTO userSearchDTO = new UserSearchDTO(currentPage, pageSize, UserRole.STUDENT, courseId);

        List<User> foundUsersWithRoleStudent = userDAO.getUsersByRoleAndCourseId(userSearchDTO);
        List<StudentDTO> students = getStudentsDTOFromUserListAndFillWithAdditionalData(foundUsersWithRoleStudent);

        Integer totalElements = userDAO.countByRoleAndCourseId(UserRole.STUDENT, courseId);
        Integer totalPages = getTotalPages(totalElements, pageSize);
        Boolean last = isLastPage(currentPage, totalPages);
        Boolean first = isFirstPage(currentPage);

        return new PageResponse<>(currentPage, pageSize, last, first, totalElements, totalPages, students);
    }

    @Override
    public List<StudentDTO> getAllStudentsByCourseIdAndGradeId(Long courseId, Long gradeId) {
        List<User> foundUsers = userDAO.findAllUsersByCourseIdGradeIdAndRole(courseId, gradeId, UserRole.STUDENT);
        return getStudentsDTOFromUserListAndFillWithAdditionalData(foundUsers);
    }

    @Override
    public ParentDTO getParentById(Long parentId) {
        User user = userDAO.getById(parentId);
        Profile profile = profileDAO.getById(user.getProfile().getId());
        List<User> students = userDAO.findAllStudentsByParentId(parentId);
        Map<Long, String> studentsNameAndId = getStudentsNameAndIdMapFromList(students);

        return new ParentDTO(
                user.getId(), user.getEmail(),
                profile.getFirstName(), profile.getLastName(),
                profile.getGender(), profile.getPhoneNumber(),
                profile.getAddress(), studentsNameAndId
        );
    }

    @Override
    public ParentDTO assignParentToStudent(Long parentId, Long studentId) {
        User parentUser = userDAO.getById(parentId);
        Profile parentProfile = profileDAO.getById(parentUser.getProfile().getId());

        userDAO.assignParentToStudent(parentId, studentId);

        List<User> students = userDAO.findAllStudentsByParentId(parentId);
        Map<Long, String> studentsNameAndId = getStudentsNameAndIdMapFromList(students);

        return new ParentDTO(
                parentUser.getId(), parentUser.getEmail(),
                parentProfile.getFirstName(), parentProfile.getLastName(),
                parentProfile.getGender(), parentProfile.getPhoneNumber(),
                parentProfile.getAddress(), studentsNameAndId
        );
    }

    private Map<Long, String> getStudentsNameAndIdMapFromList(List<User> students) {
        Map<Long, String> childrenNameAndId = new HashMap<>();

        for (User student : students) {
            String fullName = profileDAO.getById(student.getProfile().getId()).getFullName();
            childrenNameAndId.put(student.getId(), fullName);
        }

        return childrenNameAndId;
    }

    private List<StudentDTO> getStudentsDTOFromUserListAndFillWithAdditionalData(List<User> users) {
        List<StudentDTO> students = new ArrayList<>();
        for (User student : users) {
            Profile profile = profileDAO.getById(student.getProfile().getId());

            int currentStudyYear = getCurrentStudyYear();
            Grade currentGrade = gradeDAO
                    .findGradeOfStudentByYears(student.getId(), currentStudyYear, currentStudyYear + 1);

            List<User> parents = userDAO.findAllParentsByStudentId(student.getId());

            Map<Long, String> parentsNameAndId = new HashMap<>();

            for (User parent : parents) {
                Profile parentProfile = profileDAO.getById(parent.getId());

                parentsNameAndId.put(parent.getId(), parentProfile.getFullName());
            }

            StudentDTO studentDTO = new StudentDTO
                    .Builder(student.getId(), student.getEmail(), profile.getFirstName(), profile.getLastName(),
                    profile.getGender(), profile.getPhoneNumber())
                    .setCurrentGradeId(currentGrade.getId())
                    .setCurrentGradeNumber(currentGrade.getNumber())
                    .setCurrentGradeSubGroup(currentGrade.getSubgroup())
                    .setParentsNameAndId(parentsNameAndId)
                    .build();

            students.add(studentDTO);
        }
        return students;
    }

    private int getCurrentStudyYear() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        if (month >= 1 && month <= 6) {
            year--;
        }

        return year;
    }

    private Integer getTotalPages(Integer totalElements, Integer pageSize) {
        return (totalElements % pageSize >= 1)
                ? (totalElements / pageSize) + 1
                : (totalElements / pageSize);
    }

    private Boolean isLastPage(Integer currentPage, Integer totalPages) {
        return currentPage.equals(totalPages);
    }

    private Boolean isFirstPage(Integer currentPage) {
        return currentPage == 1;
    }
}
