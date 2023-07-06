package org.dcuciuc.course;

import org.dcuciuc.user.enums.UserRole;

import java.util.List;

public interface CourseService {

    List<Course> findCoursesByUserIdAndRole(Long userId, UserRole role);

    void generateReportForCourseAndGrade(Long courseId, Long gradeId, int year, int month);
}
