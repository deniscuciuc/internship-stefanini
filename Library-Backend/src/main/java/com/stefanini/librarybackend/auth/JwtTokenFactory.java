package com.stefanini.librarybackend.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.dto.AuthResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is one of the helpers.
 * Class represents factory helper class for jwt token
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
@Slf4j
public class JwtTokenFactory {

    /**
     * Method generates access token and refresh token
     * @param authentication that have AppUser
     * @return AuthResponseDto with all information of user plus tokens
     */
    public static AuthResponseDto generateAccessAndRefreshToken(Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .withClaim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        log.info("access_token created");

        String refresh_token = JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 180 * 60 * 1000))
                .sign(algorithm);

        log.info("refresh_token created");


        // Output access_token and refresh_token in body (JSON)
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);


        User user = appUser.getUser();
        return new AuthResponseDto(
                user.getId(),
                user.getEmail(),
                access_token,
                refresh_token,
                user.getRoles(),
                user.isHasTemporaryPassword(),
                user.isConfirmedByEmail()
        );

    }
}
