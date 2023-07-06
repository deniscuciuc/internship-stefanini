package org.dcuciuc.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // TODO: Add addCourse method

    @GetMapping
    public Course getCourseById(@RequestParam Long id) {
        return null;
    }


    // TODO: Add addTeacherToCourseAndGrade
    // TODO: Add getTeacherCoursesAndGrades
    // TODO: Add getStudentCourses
    // TODO: Add getStudentCoursesByStudyYear
    // TODO: Add disableStudentToCourseAndGrade. Here we can add StudentStatus in users_courses_grades to know if student
    // TODO: Add addStudentOnCourseByTeacher
}
