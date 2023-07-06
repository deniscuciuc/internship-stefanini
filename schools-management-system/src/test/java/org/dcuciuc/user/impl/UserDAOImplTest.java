package org.dcuciuc.user.impl;

import org.dcuciuc.TestData;
import org.dcuciuc.course.Course;
import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.dto.filtres.UserSearchDTO;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.impl.ProfileDAOImpl;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.dcuciuc.user.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDAOImplTest {


    @Autowired
    private ProfileDAOImpl profileDAO;

    @Autowired
    private CourseDAO<Course> courseDAO;

    @Autowired
    private GradeDAO<Grade> gradeDAO;

    @Autowired
    private UserDAO<User> underTest;
    private List<User> testUsers;
    private Profile testProfile;

    @BeforeAll
    void initTestRecords() {
        testUsers = new ArrayList<>();
        testProfile = profileDAO.create(TestData.getTestProfile());
    }

    @Test
    void shouldCreateAndReturnUserWithId() {
        User user = getTestUserWithStudentRole();
        user = underTest.create(user);
        assertNotNull(user);

        User foundUser = underTest.getById(user.getId());
        assertNotNull(foundUser);
        assertEquals(foundUser, user);
    }

    @Test
    void shouldUpdateAndReturnUserIfExists() {
        User userToUpdate = getTestUserWithStudentRole();
        userToUpdate = underTest.create(userToUpdate);
        testUsers.add(userToUpdate);

        User newUser = userToUpdate;
        newUser.setActive(false);
        newUser.setEmail("new.email@gmail.com");

        User updatedUser = underTest.update(newUser, userToUpdate.getId());

        assertNotNull(updatedUser);

        User foundUser = underTest.getById(updatedUser.getId());
        assertNotNull(foundUser);
        assertEquals(foundUser, updatedUser);
    }

    @Test
    void shouldRemoveUserByIdIfExists() {
        User user = getTestUserWithStudentRole();
        user = underTest.create(user);

        Long idOfRemovedProfile = underTest.removeById(user.getId());
        assertNotNull(idOfRemovedProfile);

        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(idOfRemovedProfile));
    }

    @Test
    void shouldReturnUserByIdIfExists() {
        User user = getTestUserWithStudentRole();
        user = underTest.create(user);

        User foundUser = underTest.getById(user.getId());

        assertNotNull(foundUser);
        assertEquals(foundUser, user);
    }

    @Test
    void shouldReturnUserByEmailIfExists() {
        User user = new User(
                "unique.email@gmail.com",
                "testPassword",
                UserRole.STUDENT,
                testProfile,
                true
        );
        user = underTest.create(user);

        User foundUser = underTest.findUserByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user, foundUser);

        underTest.removeById(user.getId());
    }

    @Test
    void shouldReturnUsersByCourseNameAndRoleIfExists() {
        User user = underTest.create(getTestUserWithStudentRole());
        Course course = courseDAO.create(TestData.getTestCourse());
        Grade grade = gradeDAO.create(TestData.getTestGrade());

        courseDAO.assignCourseToUser(course.getId(), user.getId(), grade.getId());

        UserSearchDTO userSearchDTO = new UserSearchDTO(1, 5, UserRole.STUDENT, course.getId());
        List<User> foundUsersByCourseNameAndRole = underTest.getUsersByRoleAndCourseId(userSearchDTO);
        assertNotNull(foundUsersByCourseNameAndRole);
        assertFalse(foundUsersByCourseNameAndRole.isEmpty());
        assertTrue(foundUsersByCourseNameAndRole.contains(user));
    }

    @Test
    void shouldReturnAllUsersByCourseIdGradeIdAndRoleIfExists() {
        User user = underTest.create(getTestUserWithStudentRole());
        Course course = courseDAO.create(TestData.getTestCourse());
        Grade grade = gradeDAO.create(TestData.getTestGrade());

        courseDAO.assignCourseToUser(course.getId(), user.getId(), grade.getId());

        List<User> foundUsersByCourseIdGradeIdAndRole = underTest
                .findAllUsersByCourseIdGradeIdAndRole(course.getId(), grade.getId(), user.getRole());
        assertNotNull(foundUsersByCourseIdGradeIdAndRole);
        assertFalse(foundUsersByCourseIdGradeIdAndRole.isEmpty());
        assertTrue(foundUsersByCourseIdGradeIdAndRole.contains(user));
    }

    private User getTestUserWithStudentRole() {
        return new User(
                "example.email@gmail.com",
                "testPassword",
                UserRole.STUDENT,
                testProfile,
                true
        );
    }
}