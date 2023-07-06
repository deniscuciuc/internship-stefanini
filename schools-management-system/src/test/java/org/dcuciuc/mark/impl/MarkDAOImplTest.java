package org.dcuciuc.mark.impl;

import org.dcuciuc.TestData;
import org.dcuciuc.course.Course;
import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.dto.filtres.MarkFilterDTO;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.MarkDAO;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.ProfileDAO;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarkDAOImplTest {

    @Autowired
    private MarkDAO<Mark> underTest;

    @Autowired
    private ProfileDAO<Profile> profileDAO;

    @Autowired
    private UserDAO<User> userDAO;

    @Autowired
    private CourseDAO<Course> courseDAO;
    private List<Mark> testMarks;

    @BeforeAll
    void init() {
        testMarks = new ArrayList<>();
    }

    @AfterAll
    void clear() {
        for (Mark mark : testMarks) {
            underTest.removeById(mark.getId());
        }
    }

    @Test
    void shouldCreateMarkAndReturnHimWithId() {
        Mark mark = TestData.getTestMark();
        mark = underTest.create(mark);
        testMarks.add(mark);

        assertNotNull(mark);
        assertNotNull(mark.getId());

        Mark foundMark = underTest.getById(mark.getId());
        assertEquals(foundMark, mark);
    }

    @Test
    void shouldUpdateMarkIfExistsAndReturnHim() {
        Mark markToUpdate = TestData.getTestMark();
        markToUpdate = underTest.create(markToUpdate);
        testMarks.add(markToUpdate);
        Mark newMark = TestData.getTestMark();
        newMark.setId(markToUpdate.getId());

        Mark updatedMark = underTest.update(newMark, markToUpdate.getId());
        assertNotNull(updatedMark);

        Mark foundMark = underTest.getById(updatedMark.getId());
        assertEquals(foundMark, updatedMark);
    }

    @Test
    void shouldRemoveMarkByIdIfExists() {
        Mark mark = TestData.getTestMark();
        mark = underTest.create(mark);

        Long idOfRemovedMark = underTest.removeById(mark.getId());
        assertNotNull(idOfRemovedMark);

        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(idOfRemovedMark));
    }

    @Test
    void shouldReturnMarkByIdIfExists() {
        Mark mark = underTest.create(TestData.getTestMark());
        testMarks.add(mark);

        Mark foundMark = underTest.getById(mark.getId());

        assertNotNull(foundMark);
        assertEquals(foundMark, mark);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfNotExists() {
        Long fakeId = 4512L;
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(fakeId));
    }

    @Test
    void shouldCreateMarkWithStudentTeacherAndCourse() {
        Profile profile = profileDAO.create(TestData.getTestProfile());
        User user = userDAO.create(TestData.getTestUserWithStudentRole(profile));
        Course course = courseDAO.create(TestData.getTestCourse());
        Mark mark = underTest.createMark(TestData.getTestMark(), user.getId(), user.getId(), course.getId());

        MarkFilterDTO markFilterDTO = new MarkFilterDTO.Builder(user.getId(), course.getId())
                .setCurrentPage(1)
                .setPageSize(5)
                .build();

        List<Mark> studentMarks = underTest.findMarksByStudentAndCourseIdFiltered(markFilterDTO);

        assertNotNull(studentMarks);
        assertFalse(studentMarks.isEmpty());
        assertTrue(studentMarks.contains(mark));

        underTest.removeById(mark.getId());
        userDAO.removeById(user.getId());
        profileDAO.removeById(profile.getId());
        courseDAO.removeById(course.getId());
    }

    @Test
    void shouldReturnMarksByStudentIdAndCourseIdPaginatedIfExists() {
        Profile profile = profileDAO.create(TestData.getTestProfile());
        User user = userDAO.create(TestData.getTestUserWithStudentRole(profile));
        Course course = courseDAO.create(TestData.getTestCourse());

        Mark firstMark = underTest.createMark(TestData.getTestMark(), user.getId(), user.getId(), course.getId());
        Mark secondMark = underTest.createMark(TestData.getTestMark(), user.getId(), user.getId(), course.getId());

        MarkFilterDTO markFilterDTO = new MarkFilterDTO.Builder(user.getId(), course.getId())
                .setCurrentPage(1)
                .setPageSize(5)
                .build();

        List<Mark> studentMarks = underTest.findMarksByStudentAndCourseIdFiltered(markFilterDTO);

        assertNotNull(studentMarks);
        assertFalse(studentMarks.isEmpty());
        assertTrue(studentMarks.contains(firstMark));
        assertTrue(studentMarks.contains(secondMark));

        underTest.removeById(firstMark.getId());
        underTest.removeById(secondMark.getId());
        userDAO.removeById(user.getId());
        profileDAO.removeById(profile.getId());
        courseDAO.removeById(course.getId());
    }

    @Test
    void findMarksByIdOfStudentAndCourseAndTeacherAndIntervalOfDates() {
        Profile profile = profileDAO.create(TestData.getTestProfile());
        User user = userDAO.create(TestData.getTestUserWithStudentRole(profile));
        Course course = courseDAO.create(TestData.getTestCourse());

        Mark firstMark = underTest.createMark(TestData.getTestMark(), user.getId(), user.getId(), course.getId());
        Mark exceptedFoundMark = TestData.getTestMark();
        exceptedFoundMark.setCreatedAt(LocalDate.now().plusYears(1));
        exceptedFoundMark = underTest.createMark(exceptedFoundMark, user.getId(), user.getId(), course.getId());

        LocalDate from = LocalDate.of(
                exceptedFoundMark.getCreatedAt().getYear(),
                exceptedFoundMark.getCreatedAt().getMonth(),
                1
        );

        LocalDate to = LocalDate.of(
                exceptedFoundMark.getCreatedAt().getYear(),
                exceptedFoundMark.getCreatedAt().getMonth(),
                31
        );

        MarkFilterDTO markFilterDTO = new MarkFilterDTO.Builder(user.getId(), course.getId())
                .setTeacherId(user.getId())
                .setDateFrom(Date.valueOf(from))
                .setDateTo(Date.valueOf(to))
                .build();

        List<Mark> studentMarks = underTest.findAllMarksByIdOfStudentAndCourseAndTeacherAndIntervalOfDates(markFilterDTO);

        assertNotNull(studentMarks);
        assertFalse(studentMarks.isEmpty());
        assertFalse(studentMarks.contains(firstMark));
        assertTrue(studentMarks.contains(exceptedFoundMark));

        underTest.removeById(firstMark.getId());
        underTest.removeById(exceptedFoundMark.getId());
        userDAO.removeById(user.getId());
        profileDAO.removeById(profile.getId());
        courseDAO.removeById(course.getId());
    }
}
