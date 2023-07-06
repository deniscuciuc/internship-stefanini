package org.dcuciuc.report.impl.wrappers;

import org.dcuciuc.course.Course;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.user.User;

import java.time.LocalDate;
import java.util.List;

public class ExcelReportWrapper {
    private List<StudentReportWrapper> students;
    private Course course;
    private User teacher;
    private Grade grade;
    private LocalDate from;
    private LocalDate to;

    public ExcelReportWrapper(List<StudentReportWrapper> students, Course course, User teacher, Grade grade, LocalDate from,
                              LocalDate to) {
        this.students = students;
        this.course = course;
        this.teacher = teacher;
        this.grade = grade;
        this.from = from;
        this.to = to;
    }

    public List<StudentReportWrapper> getStudents() {
        return students;
    }

    public void setStudents(List<StudentReportWrapper> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
