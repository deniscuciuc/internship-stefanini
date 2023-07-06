package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.ProfileDAO;
import com.stefanini.librarybackend.domain.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ProfileDAOImpl extends DAOAbstractImpl<Profile> implements ProfileDAO<Profile> {

    public ProfileDAOImpl() {
        setClazz(Profile.class);
    }

    @Override
    public Profile findProfileByEmail(String email) {
        TypedQuery query = entityManager.createQuery("select a from UserRole a where a.email = ?1", Profile.class);
        query.setParameter(1, email);
        return (Profile) query.getSingleResult();
    }

}
