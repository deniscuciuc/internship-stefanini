package com.stefanini.librarybackend.dao;

/**
 * The class is responsible for database operations with Profile entity.
 *
 * @param <Profile>>
 * @author DCUCICOV
 * @version 0.1
 * @since 0.1
 */
public interface ProfileDAO<Profile> extends IGenericDao<Profile> {

    /**
     * Get profile by email.
     *
     * @param email of profile to be found
     * @return profile that was found
     */
    Profile findProfileByEmail(String email);
}
