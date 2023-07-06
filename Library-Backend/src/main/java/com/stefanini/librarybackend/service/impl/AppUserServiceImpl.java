package com.stefanini.librarybackend.service.impl;


import com.stefanini.librarybackend.auth.AppUser;
import com.stefanini.librarybackend.auth.JwtTokenFactory;
import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.dto.AuthResponseDto;
import com.stefanini.librarybackend.dto.LoginRequestDto;
import com.stefanini.librarybackend.service.impl.exception.InvalidEmailOrPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppUserServiceImpl implements UserDetailsService {
    private final UserDAO<User> userDAO;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public AppUserServiceImpl(UserDAOImpl userDAOImpl, @Lazy AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder) {
        this.userDAO = userDAOImpl;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = returnsUserIfExists(email);
        return new AppUser(user);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        User user = returnsUserIfExists(request.getEmail());
        verifyPassword(user.getPassword(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        return JwtTokenFactory.generateAccessAndRefreshToken(authentication);
    }

    private void verifyPassword(String userPassword, String requestPassword) {
        boolean isPasswordCorrect = passwordEncoder.matches(requestPassword, userPassword);
        if (!isPasswordCorrect) {
            log.error("Invalid password");
            throw new InvalidEmailOrPasswordException();
        }
    }

    private User returnsUserIfExists(String email) {
        User user = userDAO.findUserByEmail(email);
        if (user == null) {
            log.error("User with such email not found");
            throw new InvalidEmailOrPasswordException();
        } else {
            log.info("User with such email found: {}", email);
            return user;
        }
    }
}
