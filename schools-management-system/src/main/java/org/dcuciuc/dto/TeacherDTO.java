package org.dcuciuc.dto;

import org.dcuciuc.course.Course;

import java.util.List;
import java.util.Map;

public class TeacherDTO {
    private Long teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private String teacherEmail;
    private String teacherPhoneNumber;
    List<Course> teacherCourses;

    public TeacherDTO(Long teacherId, String teacherFirstName, String teacherLastName, String teacherEmail,
                      String teacherPhoneNumber, List<Course> teacherCourses) {
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.teacherEmail = teacherEmail;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.teacherCourses = teacherCourses;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPhoneNumber() {
        return teacherPhoneNumber;
    }

    public void setTeacherPhoneNumber(String teacherPhoneNumber) {
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public List<Course> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(List<Course> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }
}
