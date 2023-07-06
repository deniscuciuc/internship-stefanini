package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.ProfileDAO;
import com.stefanini.librarybackend.dao.impl.ProfileDAOImpl;
import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDAO<Profile> profileDao;

    public ProfileServiceImpl(ProfileDAOImpl profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public Profile updateProfile(int id, Profile user) {
        Profile profile = profileDao.getById(id);
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setPhoneNumber(user.getPhoneNumber());
        return profileDao.update(profile);
    }

    @Override
    public Profile getProfileById(int id) {
        return profileDao.getById(id);
    }
}
