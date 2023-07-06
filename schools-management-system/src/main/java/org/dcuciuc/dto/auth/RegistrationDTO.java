package org.dcuciuc.dto.auth;

import org.dcuciuc.profile.enums.Gender;
import org.dcuciuc.user.enums.UserRole;

public class RegistrationDTO {
    private String email;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phoneNumber;
    private String address;


    public RegistrationDTO() {

    }

    public RegistrationDTO(String email, String password, UserRole role, String firstName,
                           String lastName, Gender gender, String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
