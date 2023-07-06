package org.dcuciuc.profile.impl;

import org.dcuciuc.TestData;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.ProfileDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileDAOImplTest {

    @Autowired
    private ProfileDAO<Profile> underTest;
    private List<Profile> testProfiles;

    @BeforeAll
    void initTestRecords() {
        testProfiles = new ArrayList<>();
    }

    @AfterAll
    void clearAllTestRecords() {
        for (Profile profile : testProfiles) {
            underTest.removeById(profile.getId());
        }
    }

    @Test
    void shouldCreateAndReturnProfileWithId() {
        Profile profile = TestData.getTestProfile();
        profile = underTest.create(profile);
        testProfiles.add(profile);

        assertNotNull(profile);

        Profile foundProfile = underTest.getById(profile.getId());
        assertEquals(foundProfile, profile);
    }

    @Test
    void shouldUpdateProfileIfExists() {
        Profile profileToUpdate = TestData.getTestProfile();
        profileToUpdate = underTest.create(profileToUpdate);
        testProfiles.add(profileToUpdate);

        Profile newProfile = TestData.getTestProfile();
        newProfile.setFirstName("Not same");
        newProfile.setLastName("Another");
        newProfile.setId(profileToUpdate.getId());

        Profile updatedProfile = underTest.update(newProfile, profileToUpdate.getId());
        assertNotNull(updatedProfile);

        Profile foundProfile = underTest.getById(updatedProfile.getId());
        assertEquals(foundProfile, updatedProfile);
    }

    @Test
    void shouldRemoveProfileByIdIfExists() {
        Profile profile = TestData.getTestProfile();
        profile = underTest.create(profile);

        Long idOfRemovedProfile = underTest.removeById(profile.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(idOfRemovedProfile));
    }

    @Test
    void shouldReturnProfileByIdIfExists() {
        Profile profile = TestData.getTestProfile();
        profile = underTest.create(profile);
        testProfiles.add(profile);

        Profile foundProfile = underTest.getById(profile.getId());
        assertNotNull(foundProfile);
        assertEquals(foundProfile, profile);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionIfProfileNotExists() {
        Long fakeId = 1234L;
        assertThrows(EmptyResultDataAccessException.class, () -> underTest.getById(fakeId));
    }

}
