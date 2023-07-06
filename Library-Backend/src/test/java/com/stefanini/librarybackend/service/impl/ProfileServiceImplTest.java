package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.impl.ProfileDAOImpl;
import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.service.AuthorService;
import com.stefanini.librarybackend.service.ProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ProfileServiceImplTest {

    @Mock
    private ProfileDAOImpl profileDAO;

    private AutoCloseable autoCloseable;
    private ProfileService underTest;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ProfileServiceImpl(profileDAO);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for method {@link ProfileService#updateProfile(int, Profile) updateProfile} method
     */
    @Test
    void shouldUpdateProfile() {
        Profile oldProfile = new Profile(
                "Leonardo",
                "Turtle",
                "+43268974422"
        );
        Profile newProfile = new Profile(
                "Grati",
                "Mark",
                "+43268974422"
        );

        given(profileDAO.getById(oldProfile.getId()))
                .willReturn(oldProfile);

        underTest.updateProfile(oldProfile.getId(), newProfile);

        ArgumentCaptor<Profile> profileArgumentCaptor = ArgumentCaptor.forClass(Profile.class);

        verify(profileDAO)
                .update(profileArgumentCaptor.capture());

        Profile capturedProfile = profileArgumentCaptor.getValue();

        assertThat(capturedProfile.getFirstName()).isEqualTo(newProfile.getFirstName());
    }

    /**
     * Unit test for method {@link ProfileService#getProfileById(int) getProfileById} method
     */
    @Test
    void shouldReturnProfileByIdIfExists() {
        Profile profile = new Profile(
                24,
                "Leonardo",
                "Turtle",
                "+43268974422"
        );

        underTest.getProfileById(profile.getId());

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(profileDAO)
                .getById(integerArgumentCaptor.capture());

        Integer capturedId = integerArgumentCaptor.getValue();

        assertThat(capturedId).isEqualTo(profile.getId());
    }
}