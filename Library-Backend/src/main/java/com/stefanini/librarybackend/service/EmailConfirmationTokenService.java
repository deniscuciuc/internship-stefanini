package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.ConfirmationToken;
import com.stefanini.librarybackend.domain.enums.ConfirmationTokenStatus;
import com.stefanini.librarybackend.dto.RegistrationRequestDto;
import com.stefanini.librarybackend.service.impl.exception.InvalidTokenException;

/**
 * This interface perform operations with email confirmation token that should be created after registration.
 * After saving confirmation token the user will be inactive.
 * That means that user cannot log in account before he will activate his account with token
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface EmailConfirmationTokenService {

    /**
     * Confirm email confirmation token. Change it status to CONFIRMED
     *
     * @param token that will be confirmed
     * @return confirmation token status
     * @throws InvalidTokenException if token is expired, not found or already confirmed
     */
    ConfirmationTokenStatus confirmToken(String token) throws InvalidTokenException;

    /**
     * Method should be used if previous token is expired
     *
     * @param token previous token
     * @return confirmation token status
     * @throws InvalidTokenException if previous token was not found
     */
    ConfirmationTokenStatus sendNewToken(String token) throws InvalidTokenException;
}
