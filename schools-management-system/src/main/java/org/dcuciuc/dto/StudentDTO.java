package org.dcuciuc.dto;

import org.dcuciuc.profile.enums.Gender;

import java.util.Map;
import java.util.Objects;

public class StudentDTO {

    private final Long studentId;
    private final String studentEmail;
    private final String studentFirstName;
    private final String studentLastName;
    private final Gender studentGender;
    private final String studentPhoneNumber;

    private final String studentAddress;
    private final Long currentGradeId;
    private final int currentGradeNumber;
    private final Character currentGradeSubGroup;
    private final Map<Long, String> parentsNameAndId;

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public Gender getStudentGender() {
        return studentGender;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public Long getCurrentGradeId() {
        return currentGradeId;
    }

    public int getCurrentGradeNumber() {
        return currentGradeNumber;
    }

    public Character getCurrentGradeSubGroup() {
        return currentGradeSubGroup;
    }

    public Map<Long, String> getParentsNameAndId() {
        return parentsNameAndId;
    }

    public StudentDTO(Builder builder) {
        this.studentId = builder.studentId;
        this.studentEmail = builder.studentEmail;
        this.studentFirstName = builder.studentFirstName;
        this.studentLastName = builder.studentLastName;
        this.studentGender = builder.studentGender;
        this.studentPhoneNumber = builder.studentPhoneNumber;
        this.studentAddress = builder.studentAddress;
        this.currentGradeId = builder.currentGradeId;
        this.currentGradeNumber = builder.currentGradeNumber;
        this.currentGradeSubGroup = builder.currentGradeSubGroup;
        this.parentsNameAndId = builder.parentsNameAndId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudentDTO studentDTO = (StudentDTO) obj;

        return Objects.equals(studentId, studentDTO.studentId);
    }

    public static class Builder {
        private Long studentId;
        private String studentEmail;
        private String studentFirstName;
        private String studentLastName;
        private Gender studentGender;
        private String studentPhoneNumber;

        private String studentAddress;
        private Long currentGradeId;
        private int currentGradeNumber;
        private Character currentGradeSubGroup;
        private Map<Long, String> parentsNameAndId;

        public Builder(Long studentId, String studentEmail, String studentFirstName, String studentLastName,
                       Gender studentGender, String studentPhoneNumber) {
            this.studentId = studentId;
            this.studentEmail = studentEmail;
            this.studentFirstName = studentFirstName;
            this.studentLastName = studentLastName;
            this.studentGender = studentGender;
            this.studentPhoneNumber = studentPhoneNumber;
        }

        public Builder setStudentAddress(String studentAddress) {
            this.studentAddress = studentAddress;
            return this;
        }

        public Builder setCurrentGradeId(Long currentGradeId) {
            this.currentGradeId = currentGradeId;
            return this;
        }

        public Builder setCurrentGradeNumber(int currentGradeNumber) {
            this.currentGradeNumber = currentGradeNumber;
            return this;
        }

        public Builder setCurrentGradeSubGroup(Character currentGradeSubGroup) {
            this.currentGradeSubGroup = currentGradeSubGroup;
            return this;
        }

        public Builder setParentsNameAndId(Map<Long, String> parentsNameAndId) {
            this.parentsNameAndId = parentsNameAndId;
            return this;
        }

        public StudentDTO build() {
            return new StudentDTO(this);
        }
    }
}
