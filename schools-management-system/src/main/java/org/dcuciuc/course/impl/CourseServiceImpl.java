package org.dcuciuc.course.impl;

import org.dcuciuc.course.CourseDAO;
import org.dcuciuc.course.Course;
import org.dcuciuc.course.CourseService;
import org.dcuciuc.dto.filtres.MarkFilterDTO;
import org.dcuciuc.report.impl.wrappers.ExcelReportWrapper;
import org.dcuciuc.report.impl.wrappers.StudentReportWrapper;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.grade.GradeDAO;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.MarkDAO;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.ProfileDAO;
import org.dcuciuc.report.ReportService;
import org.dcuciuc.user.User;
import org.dcuciuc.user.UserDAO;
import org.dcuciuc.user.enums.UserRole;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDAO<Course> courseDAO;
    private final UserDAO<User> userDAO;
    private final ProfileDAO<Profile> profileDAO;
    private final GradeDAO<Grade> gradeDAO;
    private final MarkDAO<Mark> markDAO;
    private final ReportService reportService;

    public CourseServiceImpl(CourseDAO<Course> courseDAO, UserDAO<User> userDAO, ProfileDAO<Profile> profileDAO,
                             GradeDAO<Grade> gradeDAO, MarkDAO<Mark> markDAO, ReportService reportService) {
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
        this.profileDAO = profileDAO;
        this.gradeDAO = gradeDAO;
        this.markDAO = markDAO;
        this.reportService = reportService;
    }


    @Override
    public List<Course> findCoursesByUserIdAndRole(Long userId, UserRole role) {
        return null;
    }

    @Override
    public void generateReportForCourseAndGrade(Long courseId, Long gradeId, int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = LocalDate.of(year, month, 31);

        User teacher = userDAO.findAllUsersByCourseIdGradeIdAndRole(courseId, gradeId, UserRole.TEACHER).get(0);
        teacher.setProfile(profileDAO.getById(teacher.getProfile().getId()));

        Course course = courseDAO.getById(courseId);
        Grade grade = gradeDAO.getById(gradeId);

        List<User> students = userDAO.findAllUsersByCourseIdGradeIdAndRole(courseId, gradeId, UserRole.STUDENT);

        List<StudentReportWrapper> studentReportWrappers =
                getStudentListWithMarksAndProfile(students, courseId,teacher.getId(), from, to);

        ExcelReportWrapper excelReportWrapper = new ExcelReportWrapper(
                studentReportWrappers, course, teacher, grade, from, to
        );

        reportService.generateExcelReport(excelReportWrapper);
    }

    private List<StudentReportWrapper> getStudentListWithMarksAndProfile(List<User> students, Long courseId, Long teacherId,
                                                               LocalDate from, LocalDate to) {
        List<StudentReportWrapper> studentReportWrappers = new ArrayList<>();
        for (User student : students) {
            student.setProfile(profileDAO.getById(student.getProfile().getId()));

            MarkFilterDTO markFilterDTO = new MarkFilterDTO.Builder(student.getId(), courseId)
                    .setTeacherId(teacherId)
                    .setDateFrom(Date.valueOf(from))
                    .setDateTo(Date.valueOf(to))
                    .build();

            List<Mark> marks = markDAO.findAllMarksByIdOfStudentAndCourseAndTeacherAndIntervalOfDates(markFilterDTO);

            StudentReportWrapper studentReportWrapper = new StudentReportWrapper(student, marks);
            studentReportWrappers.add(studentReportWrapper);
        }
        return studentReportWrappers;
    }
}
