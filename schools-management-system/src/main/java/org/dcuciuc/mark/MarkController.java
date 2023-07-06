package org.dcuciuc.mark;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    // TODO: Add markStudentOnCourseByTeacher
    // TODO: Add getStudentsMarksByCourseAndPeriodOfDates.Response pageable with info about period of dates, course and marks with teachers
    // TODO:
}
