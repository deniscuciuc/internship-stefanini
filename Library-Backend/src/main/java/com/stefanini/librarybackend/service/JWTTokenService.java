package com.stefanini.librarybackend.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This service responds for operations with refresh or access token
 *
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface JWTTokenService {

    /**
     * Refresh JWT token for user
     *
     * @param request
     * @param response
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
