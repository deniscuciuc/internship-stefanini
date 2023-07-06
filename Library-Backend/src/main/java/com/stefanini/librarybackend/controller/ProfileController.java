package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.service.impl.ProfileServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileServiceImpl profileService;

    public ProfileController(ProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'LIBRARIAN', 'ADMIN')")
    public Profile updateProfile(@PathVariable int id, @RequestBody Profile user) {
        return profileService.updateProfile(id, user);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'LIBRARIAN', 'ADMIN')")
    public Profile getProfile(@PathVariable int id) {
        return profileService.getProfileById(id);
    }

}

