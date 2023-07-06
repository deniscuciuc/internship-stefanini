package org.dcuciuc.course.impl;

import org.dcuciuc.TestData;
import org.dcuciuc.course.Course;
import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.ProfileDAO;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
import org.junit.jupiter.api.AfterAll;
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
class CourseDAOImplImplTest {

    @Autowired
    private CourseDAO<Course> underTest;

    @Autowired
    private ProfileDAO<Profile> profileDAO;

    @Autowired
    private UserDAO<User> userDAO;

    @Autowired
    private GradeDAO<Grade> gradeDAO;
    private List<Course> testCourses;

    @BeforeAll
    void init() {
        testCourses = new ArrayList<>();
    }

    @AfterAll
    void clear() {
        for (Course course : testCourses) {
            underTest.removeById(course.getId());
        }
    }

    @Test
    void shouldCreateCourseAndReturnHimWithId() {
        Course course = TestData.getTestCourse();

        course = underTest.create(course);

        testCourses.add(course);

        assertNotNull(course);
        assertNotNull(course.getId());

        Course foundCourse = underTest.getById(course.getId());
        assertEquals(foundCourse, course);
    }

    @Test
    void shouldUpdateCourseIfExistsAndReturnHim() {
        Course courseToUpdate = TestData.getTestCourse();
        courseToUpdate = underTest.create(courseToUpdate);
        testCourses.add(courseToUpdate);

        Course newCourse = TestData.getTestCourse();
        newCourse.setName("Another name");
        newCourse.setId(courseToUpdate.getId());

        Course updatedCourse = underTest.update(newCourse, courseToUpdate.getId());

        assertNotNull(updatedCourse);
        assertNotNull(updatedCourse.getId());
        assertEquals(updatedCourse, newCourse);

        Course actualFoundCourse = underTest.getById(updatedCourse.getId());
        assertEquals(actualFoundCourse, updatedCourse);
    }

    @Test
    void shouldRemoveCourseByIdIfExists() {
        Course course = TestData.getTestCourse();
        course = underTest.create(course);

        Long idOfRemovedCourse = underTest.removeById(course.getId());
        assertNotNull(idOfRemovedCourse);
        assertEquals(idOfRemovedCourse, course.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(idOfRemovedCourse));
    }

    @Test
    void shouldReturnCourseByIdIfExists() {
        Course course = TestData.getTestCourse();
        course = underTest.create(course);
        testCourses.add(course);

        Course foundCourse = underTest.getById(course.getId());
        assertNotNull(foundCourse);
        assertEquals(foundCourse, course);
    }

    @Test
    void shouldTrowEntityNotFoundExceptionIfCourseNotExists() {
        Long fakeId = 5234L;
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(fakeId));
    }

    @Test
    void shouldReturnCoursesByUserIdCourseIdAndGradeIdIfExists() {
        Course course = TestData.getTestCourse();
        course = underTest.create(course);
        testCourses.add(course);

        Grade grade = new Grade(2022, 2023, 9, 'a');
        grade = gradeDAO.create(grade);

        Profile userProfile = TestData.getTestProfile();
        userProfile = profileDAO.create(userProfile);
        User user = TestData.getTestUserWithStudentRole(userProfile);
        user = userDAO.create(user);
        underTest.assignCourseToUser(course.getId(), user.getId(), grade.getId());

        List<Course> coursesFoundByUserId = underTest.findAllCoursesByUserId(user.getId());
        assertNotNull(coursesFoundByUserId);
        assertFalse(coursesFoundByUserId.isEmpty());
        assertTrue(coursesFoundByUserId.contains(course));


        underTest.removeUserFromCourses(user.getId());
        userDAO.removeById(user.getId());
        profileDAO.removeById(userProfile.getId());
        gradeDAO.removeById(grade.getId());
    }

    @Test
    void shouldRemoveUserFromCourseIfSuchExists() {
        Course course = TestData.getTestCourse();
        course = underTest.create(course);
        testCourses.add(course);

        Grade grade = new Grade(2022, 2023, 9, 'a');
        grade = gradeDAO.create(grade);

        Profile userProfile = TestData.getTestProfile();
        userProfile = profileDAO.create(userProfile);
        User user = TestData.getTestUserWithStudentRole(userProfile);
        user = userDAO.create(user);

        underTest.assignCourseToUser(course.getId(), user.getId(), grade.getId());

        List<Course> coursesFoundByUserId = underTest.findAllCoursesByUserId(user.getId());
        assertNotNull(coursesFoundByUserId);
        assertFalse(coursesFoundByUserId.isEmpty());
        assertTrue(coursesFoundByUserId.contains(course));

        underTest.removeUserFromCourse(course.getId(), user.getId(), grade.getId());

        userDAO.removeById(user.getId());
        profileDAO.removeById(userProfile.getId());
        gradeDAO.removeById(grade.getId());
    }
}
