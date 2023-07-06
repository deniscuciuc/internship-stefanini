package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.History;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.email.EmailSenderService;
import com.stefanini.librarybackend.service.UserService;
import com.stefanini.librarybackend.service.helper.ValueChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.stefanini.librarybackend.helper.PasswordGenerator.generateRandomPassword;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDAO<User> userDao;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final Environment environment;

    public UserServiceImpl(UserDAOImpl userDao, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService, Environment environment) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.environment = environment;
    }

    @Override
    public User createUser(User user) {
        String password = generateRandomPassword();
        user.setPassword(password);
        user.setConfirmedByEmail(true);
        user.setHasTemporaryPassword(true);
        String appHomePage = environment.getProperty("CORS_ALLOWED_ORIGINS") + "/";
        String email = "Hello, " + user.getProfile().getFirstName() + " " + user.getProfile().getLastName() + "!"
                + " Here is your password for Stefanini Library Application " + password
                + " To use the application please visit " + appHomePage;
        String subject = "Registration info";
        emailSenderService.sendMail(user.getEmail(), email, subject);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.create(user);
    }

    @Override
    public User updateUser(int id, User user) {
        User u = userDao.getById(id);
        u.setEmail(user.getEmail());

        u.getProfile().setFirstName(user.getProfile().getFirstName());
        u.getProfile().setLastName(user.getProfile().getLastName());
        u.getProfile().setPhoneNumber(user.getProfile().getPhoneNumber());

        if (user.getPassword() != null) {
            u.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userDao.update(u);
    }

    @Override
    public List<User> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        ValueChecker.verifyPagingAndSortingValues(pageNumber, pageSize, sortBy, sortOrder);
        return userDao.getAllSortedAndPaginated(pageNumber, pageSize, sortBy, sortOrder);
    }

    @Override
    public User findById(int id) {
        return userDao.getById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public int deleteById(int id) {
        return userDao.removeById(id);
    }

    @Override
    @Transactional
    public User assignRole(int id, Role role) {
        User user = userDao.getById(id);
        user.getRoles().add(role);
        log.info("Role {} was assigned to user with id {}", role.name(), user.getId());
        return userDao.update(user);
    }

    @Override
    public List<History> getUserHistory(int userId) {
        return userDao.getById(userId).getHistory();
    }


    @Override
    public List<Book> getUserBooks(int userId) {
        return userDao.getById(userId).getBook();
    }

    @Override
    public User changePassword(int id, String password) {
        User u = userDao.getById(id);
        u.setHasTemporaryPassword(false);
        u.setPassword(passwordEncoder.encode(password));
        return userDao.update(u);
    }


    public List<User> findUserByAnyCriteria(String criteria) {
        return userDao.getUsersByCriteria(criteria);
    }

    @Override
    public void sendLinkForChangePassword(User user) {
        String link = environment.getProperty("CORS_ALLOWED_ORIGINS") + "/resetPassword/";
        String message = "Hello, please access the link to update your password " + link
                + user.getId() + "/" + user.getEmail();
        String subject = "Forgot password";
        log.info(message);
        emailSenderService.sendMail(user.getEmail(), message, subject);
    }

    @Override
    public Long getNumberOfUsers() {
        return userDao.getNumberOf();
    }

}