package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.History;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;

import java.util.List;

/**
 * Class is used to manage users
 *
 * @author DCUCICOV
 * @version 0.1
 * @since 0.1
 */
public interface UserService {

    /**
     * Create user.
     *
     * @param user to be created
     * @return created user
     */
    User createUser(User user);

    /**
     * Update user.
     *
     * @param id   of user to be updated
     * @param user with new data to be saved
     * @return updated user
     */
    User updateUser(int id, User user);

    /**
     * Get all users.
     *
     * @return list of all users
     */
    List<User> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder);

    /**
     * Get user by id.
     *
     * @param id of user to be found
     * @return user that was found
     */
    User findById(int id);

    /**
     * Get user by email.
     *
     * @param email of user to be found
     * @return user that was found
     */
    User findByEmail(String email);

    /**
     * Delete user by id.
     *
     * @param id of user to be deleted
     * @return id of user that was deleted
     */
    int deleteById(int id);

    /**
     * Assign role to user.
     *
     * @param id   of user to assign role
     * @param role to be assigned
     * @return user with updated role
     */
    User assignRole(int id, Role role);

    /**
     * Get user's history by user id.
     *
     * @param userId to be found
     * @return list of user's history
     */
    List<History> getUserHistory(int userId);

    /**
     * Get all user's books.
     *
     * @param userId to be found
     * @return list of user's books
     */
    List<Book> getUserBooks(int userId);

    /**
     * Get user by any criteria.
     *
     * @param criteria by which the book will be searched for
     * @return list of users that was found by criteria
     */
    List<User> findUserByAnyCriteria(String criteria);

    /**
     * Change user's password.
     *
     * @param id       of user
     * @param password to save
     * @return user with updated password
     */
    User changePassword(int id, String password);

    /**
     * Send link to email to change password.
     *
     * @param user
     */
    void sendLinkForChangePassword(User user);

    /**
     * Return number of users.
     *
     * @return long number of users
     */
    Long getNumberOfUsers();
}
