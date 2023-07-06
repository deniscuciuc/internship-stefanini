package org.dcuciuc.grade.impl;

import org.dcuciuc.TestData;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GradeDAOImplTest {

    @Autowired
    private GradeDAO<Grade> underTest;

    @Autowired
    private UserDAO<User> userDAO;

    @Autowired
    private ProfileDAO<Profile> profileDAO;

    private List<Grade> testGrades;

    @BeforeAll
    void setUp() {
        testGrades = new ArrayList<>();
    }

    @AfterAll
    void tearDown() {
        for (Grade grade : testGrades) {
            underTest.removeById(grade.getId());
        }
    }

    @Test
    void shouldCreateGradeAndReturnIt() {
        Grade toCreate = TestData.getTestGrade();
        toCreate = underTest.create(toCreate);
        testGrades.add(toCreate);

        assertNotNull(toCreate);
        assertNotNull(toCreate.getId());

        Grade foundGrade = underTest.getById(toCreate.getId());
        assertNotNull(foundGrade);
        assertEquals(foundGrade, toCreate);
    }

    @Test
    void shouldUpdateByIdIfExists() {
        Grade toUpdate = TestData.getTestGrade();
        toUpdate = underTest.create(toUpdate);
        testGrades.add(toUpdate);

        Grade newGrade = TestData.getTestGrade();
        newGrade.setNumber(5);
        newGrade.setId(toUpdate.getId());

        Grade updatedGrade = underTest.update(newGrade, toUpdate.getId());
        assertNotNull(updatedGrade);
        assertEquals(updatedGrade, newGrade);

        Grade foundGrade = underTest.getById(updatedGrade.getId());
        assertNotNull(foundGrade);
        assertEquals(foundGrade, updatedGrade);
    }

    @Test
    void shouldRemoveGradeByIdIfExists() {
        Grade toDelete = TestData.getTestGrade();
        toDelete = underTest.create(toDelete);

        Long idOfRemoved = underTest.removeById(toDelete.getId());
        assertNotNull(idOfRemoved);
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(idOfRemoved));
    }

    @Test
    void shouldReturnGradeByIdIfExists() {
        Grade toFound = TestData.getTestGrade();
        toFound = underTest.create(toFound);
        testGrades.add(toFound);

        Grade foundGrade = underTest.getById(toFound.getId());
        assertNotNull(foundGrade);
        assertEquals(foundGrade, toFound);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfGradeWithSuchIdNotExists() {
        Long fakeId = 9212L;
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(fakeId));
    }

    @Test
    void shouldAssignGradeToStudent() {

    }

    @Test
    void shouldReturnCurrentGradeOfStudentByStudentIdIfExists() {
        Profile profile = TestData.getTestProfile();
        profile = profileDAO.create(profile);

        User student = TestData.getTestUserWithStudentRole(profile);
        student = userDAO.create(student);

        Grade grade = TestData.getTestGrade();
        grade = underTest.create(grade);

        Grade expectedFoundGrade = TestData.getTestGrade();
        expectedFoundGrade.setYearFrom(2024);
        expectedFoundGrade.setYearTo(2025);
        expectedFoundGrade = underTest.create(expectedFoundGrade);

        testGrades.add(grade);
        testGrades.add(expectedFoundGrade);

        underTest.assignGradeToStudent(grade.getId(), student.getId());
        underTest.assignGradeToStudent(expectedFoundGrade.getId(), student.getId());

        Grade actualFoundGrade = underTest.findGradeOfStudentByYears(student.getId(),
                expectedFoundGrade.getYearFrom(), expectedFoundGrade.getYearTo());
        assertNotNull(actualFoundGrade);
        assertEquals(expectedFoundGrade, actualFoundGrade);

        underTest.removeGradesFromStudent(student.getId());
        userDAO.removeById(student.getId());
        profileDAO.removeById(profile.getId());
    }

    @Test
    void shouldRemoveGradeByStudentId() {
        Profile profile = TestData.getTestProfile();
        profile = profileDAO.create(profile);

        User student = TestData.getTestUserWithStudentRole(profile);
        student = userDAO.create(student);

        Grade grade = TestData.getTestGrade();
        grade = underTest.create(grade);

        testGrades.add(grade);

        underTest.assignGradeToStudent(grade.getId(), student.getId());

        Grade foundGrade = underTest.findGradeOfStudentByYears(student.getId(), grade.getYearFrom(), grade.getYearTo());
        assertNotNull(foundGrade);

        underTest.removeGradeFromStudent(grade.getId(), student.getId());


        userDAO.removeById(student.getId());
        profileDAO.removeById(profile.getId());
    }

    @Test
    void shouldRemoveGradesByStudentId() {
        Profile profile = TestData.getTestProfile();
        profile = profileDAO.create(profile);

        User student = TestData.getTestUserWithStudentRole(profile);
        student = userDAO.create(student);

        Grade grade9A = TestData.getTestGrade();
        grade9A = underTest.create(grade9A);
        Grade grade10A = TestData.getTestGrade();
        grade10A.setYearFrom(grade9A.getYearFrom() + 1);
        grade10A.setYearFrom(grade9A.getYearTo() + 1);
        grade10A = underTest.create(grade10A);

        testGrades.add(grade9A);
        testGrades.add(grade10A);

        underTest.assignGradeToStudent(grade9A.getId(), student.getId());
        underTest.assignGradeToStudent(grade10A.getId(), student.getId());

        Grade actualFoundGrade = underTest.findGradeOfStudentByYears(student.getId(),
                grade9A.getYearFrom(), grade9A.getYearTo());
        assertNotNull(actualFoundGrade);

        underTest.removeGradesFromStudent(student.getId());

        userDAO.removeById(student.getId());
        profileDAO.removeById(profile.getId());
    }
}
