package com.stefanini.librarybackend.dao;


/**
 * The class is responsible for database operations with ConfirmationToken entity.
 *
 * @param <ConfirmationToken>>
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface EmailConfirmationTokenDAO<ConfirmationToken> extends IGenericDao<ConfirmationToken> {

    /**
     * Get confirmation token by token from database.
     *
     * @param token to be found
     * @return founded token
     */
    ConfirmationToken findByToken(String token);
}
