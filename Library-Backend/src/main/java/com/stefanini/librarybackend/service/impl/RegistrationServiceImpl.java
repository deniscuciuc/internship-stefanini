package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.EmailConfirmationTokenDAO;
import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.EmailConfirmationTokenDAOImpl;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.ConfirmationToken;
import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.dto.RegistrationRequestDto;
import com.stefanini.librarybackend.email.EmailSenderService;
import com.stefanini.librarybackend.service.RegistrationService;
import com.stefanini.librarybackend.service.impl.exception.EmailAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final String EMAIL_ALREADY_TAKEN_MSG = "Email %s taken";

    private final PasswordEncoder passwordEncoder;
    private final UserDAO<User> userDAO;
    private final EmailConfirmationTokenDAO<ConfirmationToken> emailConfirmationTokenDAO;
    private final EmailSenderService emailSenderService;
    private final Environment environment;

    public RegistrationServiceImpl(UserDAOImpl userDAOImpl, PasswordEncoder passwordEncoder,
                                   EmailConfirmationTokenDAOImpl emailConfirmationTokenDAOImpl,
                                   EmailSenderService emailSenderService, Environment environment) {
        this.userDAO = userDAOImpl;
        this.passwordEncoder = passwordEncoder;
        this.emailConfirmationTokenDAO = emailConfirmationTokenDAOImpl;
        this.emailSenderService = emailSenderService;
        this.environment = environment;
    }

    @Override
    public User registerUser(RegistrationRequestDto request) {
        User user = userDAO.findUserByEmail(request.getEmail());
        User newUser;

        if (user != null) {
            log.error(String.format(EMAIL_ALREADY_TAKEN_MSG, user.getEmail()));
            throw new EmailAlreadyTakenException();
        } else {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            newUser = new User(request.getEmail(), encodedPassword,
                    new Profile(request.getFirstName(), request.getLastName(), request.getPhoneNumber()));

            newUser.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
            newUser.setConfirmedByEmail(false);

            userDAO.create(newUser);

            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = ConfirmationToken.createConfirmationToken(token, newUser);
            emailConfirmationTokenDAO.create(confirmationToken);

            log.info("Email confirmation token saved in database");

            String link = environment.getProperty("CORS_ALLOWED_ORIGINS") + "/email-confirmation/" + token;
            emailSenderService.sendMail(
                    request.getEmail(),
                    "Activate your account by this link - " + link + "\n Link will expired in 15 minutes",
                    "Confirm your email"
            );
        }
        return newUser;
    }
}
