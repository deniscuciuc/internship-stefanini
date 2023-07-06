package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.email.EmailSenderService;
import com.stefanini.librarybackend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceImplTest {

    @Mock
    private UserDAOImpl userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private Environment environment;

    private AutoCloseable autoCloseable;
    private UserService underTest;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserServiceImpl(userDAO, passwordEncoder, emailSenderService, environment);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for {@link UserService#createUser(User) createUser} method
     */
    @Test
    void shouldCreateUserAndReturnHimWithTemporaryPasswordAndConfirmedEmail() {
        User user = new User(
                "random.email@gmail.com"
        );
        user.setProfile(new Profile(
                "Smith", "Alan", "+475882299"
        ));

        underTest.createUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userDAO)
                .create(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();


        assertThat(capturedUser.isConfirmedByEmail()).isEqualTo(true);
        assertThat(capturedUser.isHasTemporaryPassword()).isEqualTo(true);
    }

    /**
     * Unit test for {@link UserService#updateUser(int, User) updateUser} method
     */
    @Test
    void shouldUpdateUserIfExists() {
        User oldUser = new User(
                "random.email@gmail.com",
                new Profile(
                        "Key",
                        "Hay",
                        "+47829981"
                )
        );

        User newUser = new User(
                "email.user@gmail.com",
                new Profile(
                        "Genre",
                        "Alan",
                        "+422112"
                )
        );

        given(userDAO.getById(oldUser.getId()))
                .willReturn(oldUser);

        underTest.updateUser(oldUser.getId(), newUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userDAO)
                .update(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(capturedUser.getProfile().getFirstName()).isEqualTo(newUser.getProfile().getFirstName());
        assertThat(capturedUser.getProfile().getLastName()).isEqualTo(newUser.getProfile().getLastName());
        assertThat(capturedUser.getProfile().getPhoneNumber()).isEqualTo(newUser.getProfile().getPhoneNumber());
    }

    /**
     * Unit test for {@link UserService#getAllUsers(int, int, String, String) showAllUsers} method
     */
    @Test
    @Disabled
    void shouldReturnAllUsersIfExists() {

    }

    /**
     * Unit test for {@link UserService#findById(int) findById} method
     */
    @Test
    void shouldFindAndReturnUserByIdIfExists() {
        int userId = 2;

        underTest.findById(userId);

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(userDAO)
                .getById(integerArgumentCaptor.capture());

        int capturedId = integerArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(userId);
    }

    /**
     * Unit test for {@link UserService#findByEmail(String) findByEmail} method
     */
    @Test
    void shouldFindAndReturnUserByEmailIfExists() {
        String userEmail = "email@gmail.com";

        underTest.findByEmail(userEmail);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(userDAO)
                .findUserByEmail(stringArgumentCaptor.capture());

        String capturedEmail = stringArgumentCaptor.getValue();

        assertThat(capturedEmail).isEqualTo(userEmail);
    }

    /**
     * Unit test for {@link UserService#deleteById(int) deleteById} method
     */
    @Test
    void shouldDeleteUserByIdIfExists() {
        int userId = 2;

        underTest.deleteById(userId);

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(userDAO)
                .removeById(integerArgumentCaptor.capture());

        int capturedId = integerArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(userId);
    }

    /**
     * Unit test for {@link UserService#assignRole(int, Role) assignRole} method
     */
    @Test
    void shouldAssignRoleToUserIfExists() {

    }

    /**
     * Unit test for {@link UserService#getUserHistory(int) getUserHistory} method
     */
    @Test
    void getUserHistory() {
    }

    /**
     * Unit test for {@link UserService#getUserBooks(int) getUserBooks} method
     */
    @Test
    void getUserBooks() {
    }

    /**
     * Unit test for {@link UserService#changePassword(int, String) changePassword} method
     */
    @Test
    void changePassword() {
    }

    /**
     * Unit test for {@link UserService#findUserByAnyCriteria(String) findUserByAnyCriteria} method
     */
    @Test
    void findUserByAnyCriteria() {
    }

    /**
     * Unit test for {@link UserService#sendLinkForChangePassword(User) sendLinkForChangePassword} method
     */
    @Test
    void sendLinkForChangePassword() {
    }
}