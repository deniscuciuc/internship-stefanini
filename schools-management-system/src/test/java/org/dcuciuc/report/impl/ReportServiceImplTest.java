/*
package org.dcuciuc.report.impl;

import org.dcuciuc.course.Course;
import org.dcuciuc.report.impl.wrappers.ExcelReportWrapper;
import org.dcuciuc.report.impl.wrappers.StudentReportWrapper;
import org.dcuciuc.user.grade.Grade;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.enums.MarkType;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.enums.Gender;
import org.dcuciuc.report.ReportService;
import org.dcuciuc.user.User;
import org.dcuciuc.user.enums.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportServiceImplTest {

    private ReportService underTest;

    @BeforeAll
    void init() {
        //underTest = new ReportServiceImpl();
    }

    @Test
    @Disabled
    void shouldGenerateCourseReportAndSaveTheFile() {
        Course course = new Course(1L, "English");
        Grade grade = new Grade(1L, 2022, 2023, 9, 'a');
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 1, 31);

        underTest.generateExcelReport(
                new ExcelReportWrapper(
                        getTestStudents(),
                        course,
                        getTestTeacher(),
                        grade,
                        from,
                        to
                )
        );

        // todo: extract data and test values inserted

    }

    private List<StudentReportWrapper> getTestStudents() {
        List<StudentReportWrapper> students = new ArrayList<>();
        students.add(
                new StudentReportWrapper(
                        new User(1L,
                                "john.milk@gmail.com",
                                UserRole.STUDENT,
                                getTestStudentProfile(),
                                true
                        ),
                        getTestMarks()
                )
        );
        students.add(
                new StudentReportWrapper(
                        new User(2L,
                                "io.asacura@gmail.com",
                                UserRole.STUDENT,
                                getTestStudentProfile(),
                                true
                        ),
                        getTestMarks()
                )
        );
        return students;
    }

    private User getTestTeacher() {
        return new User(6L, "ana.ivanovna@gmail.com", UserRole.TEACHER, getTestTeacherProfile(), true);
    }

    private Profile getTestTeacherProfile() {
        return new Profile(
                6L,
                "Anna",
                "Ivanovna",
                Gender.FEMALE,
                "0742288",
                "Puskin 32"
        );
    }

    private Profile getTestStudentProfile() {
        return new Profile(
                1L,
                "John",
                "Milk",
                Gender.MALE,
                "068979988",
                "Puskin 31"
        );
    }


    private List<Mark> getTestMarks() {
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark(1L, MarkType.ABSENT, LocalDate.of(2023, 1, 1)));
        marks.add(new Mark(2L, MarkType.FOUR, LocalDate.of(2023, 1, 15)));
        marks.add(new Mark(3L, MarkType.SIX, LocalDate.of(2023, 1, 28)));
        marks.add(new Mark(4L, MarkType.FIVE, LocalDate.of(2023, 1, 9)));
        marks.add(new Mark(5L, MarkType.TEN, LocalDate.of(2023, 1, 10)));
        return marks;
    }
}*/
