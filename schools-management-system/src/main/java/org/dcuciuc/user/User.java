package org.dcuciuc.user;

import org.dcuciuc.profile.Profile;
import org.dcuciuc.user.enums.UserRole;

import java.util.Objects;


public class User {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private Profile profile;
    private boolean isActive;


    public User() {

    }

    public User(Long id, String email, String password, UserRole role, Profile profile, boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile = profile;
        this.isActive = isActive;
    }

    public User(Long id, String email, String password, UserRole role, Profile profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile = profile;
    }

    public User(Long id, String email, UserRole role, Profile profile, boolean isActive) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.profile = profile;
        this.isActive = isActive;
    }

    public User(Long id, String email, UserRole role, boolean isActive) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }

    public User(String email, String password, UserRole role, Profile profile, boolean isActive) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.profile = profile;
        this.isActive = isActive;
    }

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password, UserRole role, boolean isActive) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;

        boolean idMatches = Objects.equals(id, user.id);
        boolean isActiveMatches = isActive == user.isActive;

        return idMatches && isActiveMatches;
    }
}
