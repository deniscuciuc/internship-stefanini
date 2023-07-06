package org.dcuciuc.report;

import org.dcuciuc.course.CourseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final CourseService courseService;

    public ReportController(CourseService courseService) {
        this.courseService = courseService;
    }

    // TODO: Add generateReportForCourseAndGradeByYearAndMonth (method return info about report with id)
    // TODO: Add downloadReportById
}
