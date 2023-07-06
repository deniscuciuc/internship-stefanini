package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.User;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface UserService {

    /**
     * Saves user.
     *
     * @param user to be saved
     * @return saved user
     */
    User saveUser(User user);

    /**
     * Finds all users.
     *
     * @return all users or empty list
     */
    List<User> findAllUsers();

    /**
     * Delete user by its id.
     *
     * @param id of user to be deleted
     * @return id of deleted user
     */
    Long deleteUserById(Long id);
}
