package com.stefanini.librarybackend.dao;

import java.util.List;

/**
 * The class is responsible for database operations with User entity.
 *
 * @param <User>>>
 * @author DCUCICOV
 * @version 0.1
 * @since 0.1
 */
public interface UserDAO<User> extends IGenericDao<User> {

    /**
     * Get user by email.
     *
     * @param userName to be found
     * @return found user
     */
    User findUserByEmail(String userName);

    /**
     * Get all users by criteria.
     *
     * @param criteria
     * @return list of users that was found
     */
    List<User> getUsersByCriteria(String criteria);
}
