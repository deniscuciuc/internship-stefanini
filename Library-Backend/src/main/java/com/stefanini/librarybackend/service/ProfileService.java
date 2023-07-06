package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Profile;

import java.util.List;

/**
 * Class is used to manage users' profiles
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface ProfileService {

    /**
     * Update profile.
     *
     * @param id   of profile
     * @param user new profile data to save
     * @return updated profile
     */
    Profile updateProfile(int id, Profile user);

    /**
     * Get profile by id.
     *
     * @param id of profile to be found
     * @return profile that was found
     */
    Profile getProfileById(int id);

}
