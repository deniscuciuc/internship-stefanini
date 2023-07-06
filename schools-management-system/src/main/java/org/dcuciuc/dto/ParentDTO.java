package org.dcuciuc.dto;

import org.dcuciuc.profile.enums.Gender;

import java.util.Map;

public class ParentDTO {
    private Long parentId;
    private String parentEmail;
    private String parentFirstName;
    private String parentLastName;
    private Gender parentGender;
    private String parentPhoneNumber;

    private String parentAddress;
    private Map<Long, String> studentsNameAndId;

    public ParentDTO(Long parentId, String parentEmail, String parentFirstName, String parentLastName,
                     Gender parentGender, String parentPhoneNumber, String parentAddress,
                     Map<Long, String> studentsNameAndId) {
        this.parentId = parentId;
        this.parentEmail = parentEmail;
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentGender = parentGender;
        this.parentPhoneNumber = parentPhoneNumber;
        this.parentAddress = parentAddress;
        this.studentsNameAndId = studentsNameAndId;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public String getParentFirstName() {
        return parentFirstName;
    }

    public String getParentLastName() {
        return parentLastName;
    }

    public Gender getParentGender() {
        return parentGender;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public Map<Long, String> getStudentsNameAndId() {
        return studentsNameAndId;
    }
}
