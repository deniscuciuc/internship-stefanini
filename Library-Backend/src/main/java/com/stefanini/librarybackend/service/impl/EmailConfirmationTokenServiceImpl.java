package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.EmailConfirmationTokenDAO;
import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.EmailConfirmationTokenDAOImpl;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.ConfirmationToken;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.ConfirmationTokenStatus;
import com.stefanini.librarybackend.email.EmailSenderService;
import com.stefanini.librarybackend.service.EmailConfirmationTokenService;
import com.stefanini.librarybackend.service.impl.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.stefanini.librarybackend.domain.enums.ConfirmationTokenStatus.*;

@Slf4j
@Service
public class EmailConfirmationTokenServiceImpl implements EmailConfirmationTokenService {

    private final EmailConfirmationTokenDAO<ConfirmationToken> emailConfirmationTokenDAO;
    private final UserDAO<User> userDAO;
    private final EmailSenderService emailSenderService;
    private final Environment environment;

    public EmailConfirmationTokenServiceImpl(EmailConfirmationTokenDAOImpl emailConfirmationTokenDAOImpl,
                                             UserDAOImpl userDAOImpl, EmailSenderService emailSenderService, Environment environment) {
        this.emailConfirmationTokenDAO = emailConfirmationTokenDAOImpl;
        this.userDAO = userDAOImpl;
        this.emailSenderService = emailSenderService;
        this.environment = environment;
    }

    @Override
    public ConfirmationTokenStatus confirmToken(String token) throws InvalidTokenException {
        ConfirmationToken confirmationToken = emailConfirmationTokenDAO.findByToken(token);

        log.info("Token status after getting it from db: " + confirmationToken.getStatus());
        verifyToken(confirmationToken);
        log.info("Token status after verifyMethod: " + confirmationToken.getStatus());

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationToken.setStatus(CONFIRMED);
        confirmationToken.getUser().setConfirmedByEmail(true);

        userDAO.update(confirmationToken.getUser());
        emailConfirmationTokenDAO.update(confirmationToken);
        return confirmationToken.getStatus();
    }

    @Override
    public ConfirmationTokenStatus sendNewToken(String token) throws InvalidTokenException {
        ConfirmationToken oldConfirmationToken = emailConfirmationTokenDAO.findByToken(token);

        if (oldConfirmationToken == null) {
            log.error("Token not found");
            throw new InvalidTokenException("Token not found");
        }

        String newToken = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.createConfirmationToken(newToken, oldConfirmationToken.getUser());

        log.info("Confirmation token status: " + confirmationToken.getStatus());
        emailConfirmationTokenDAO.create(confirmationToken);
        log.info("Email confirmation token saved in database");

        String link = environment.getProperty("CORS_ALLOWED_ORIGINS") + "/email-confirmation/" + newToken;
        emailSenderService.sendMail(
                confirmationToken.getUser().getEmail(),
                "Activate your account by this link - " + link + "\n Link will expired in 15 minutes",
                "Confirm your email"
        );

        return confirmationToken.getStatus();
    }

    private void verifyToken(ConfirmationToken confirmationToken) throws InvalidTokenException {
        if (confirmationToken == null) {
            log.error("Token not found");
            throw new InvalidTokenException("Token not found");
        }

        if (confirmationToken.getStatus() == EXPIRED) {
            log.error("Token is expired");
            throw new InvalidTokenException("Token is expired");
        }

        boolean isExpired = LocalDateTime.now().isAfter(confirmationToken.getExpiresAt());
        if (isExpired) {
            log.error("Token is expired");
            confirmationToken.setStatus(EXPIRED);
            throw new InvalidTokenException("Token is expired");
        }
    }
}
