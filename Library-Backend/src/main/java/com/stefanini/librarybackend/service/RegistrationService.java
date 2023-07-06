package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.dto.RegistrationRequestDto;

/**
 * This class is used to register application users
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface RegistrationService {

    /**
     * Method verifies if email is not already taken (exception),
     * then if it's not password will be encoded and user will be saved in database
     *
     * @param request with registration data
     */
    User registerUser(RegistrationRequestDto request);
}
