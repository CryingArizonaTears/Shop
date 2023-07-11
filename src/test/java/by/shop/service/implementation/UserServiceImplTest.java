package by.shop.service.implementation;

import by.shop.model.Bucket;
import by.shop.model.Role;
import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;
import by.shop.repository.BucketRepository;
import by.shop.repository.UserCredentialsRepository;
import by.shop.repository.UserProfileRepository;
import by.shop.service.RoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserProfileRepository userProfileRepository;

    @Mock
    RoleService roleService;

    @Mock
    UserCredentialsRepository userCredentialsRepository;

    @Mock
    BucketRepository bucketRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    final UserProfile userProfileForTesting = new UserProfile();
    final UserProfile expectingUserProfile = new UserProfile();
    final UserCredentials userCredentialsForTesting = new UserCredentials();
    final UserCredentials expectingUserCredentials = new UserCredentials();
    final Bucket expectingBucket = new Bucket();
    final Role roleUserForTesting = new Role();


    @BeforeEach
    void beforeTests() {
        userCredentialsForTesting.setPassword("testPassword");
        userCredentialsForTesting.setUsername("testUsername");
        expectingUserCredentials.setPassword("encodedPassword");
        expectingUserCredentials.setUsername("testUsername");
        roleUserForTesting.setName("ROLE_USER");
        expectingBucket.setTotalPrice(BigDecimal.valueOf(0));
        userProfileForTesting.setUserCredentials(userCredentialsForTesting);
        userProfileForTesting.setRole(roleUserForTesting);
        userProfileForTesting.setPhone("testPhone");
        userProfileForTesting.setAddress("testAddress");
        userProfileForTesting.setEmail("testEmail");
        userProfileForTesting.setFullName("testFullname");
        expectingUserProfile.setBucket(expectingBucket);
        expectingUserProfile.setUserCredentials(expectingUserCredentials);
        expectingUserProfile.setRole(roleUserForTesting);
        expectingUserProfile.setPhone("testPhone");
        expectingUserProfile.setAddress("testAddress");
        expectingUserProfile.setEmail("testEmail");
        expectingUserProfile.setFullName("testFullname");
    }

    @Test
    void testGetAll_Successful() {
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfileForTesting);
        when(userProfileRepository.findAll())
                .thenReturn(userProfiles);
        List<UserProfile> expectingUserProfiles = userService.getAll();
        assertEquals(userProfiles, expectingUserProfiles);
    }

    @Test
    void testGetById_Successful() {
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        UserProfile expectingUserProfile = userService.getById(1L);
        assertEquals(userProfileForTesting, expectingUserProfile);
    }

    @Test
    void testCreateUserAsAdmin_Successful() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        userService.createAsAdmin(userProfileForTesting);
        verify(userCredentialsRepository).save(argThat(userCredentialsForSaving ->
                userCredentialsForSaving.equals(expectingUserCredentials)));
        verify(userProfileRepository).save(argThat(userProfileDtoForSave ->
                userProfileDtoForSave.equals(expectingUserProfile)));
    }

    @Test
    void testCreateUserAsGuest_Successful() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(roleService.getByName("ROLE_USER")).
                thenReturn(roleUserForTesting);
        userService.createAsGuest(userProfileForTesting);
        verify(userCredentialsRepository).save(argThat(userCredentialsForSaving ->
                userCredentialsForSaving.equals(expectingUserCredentials)));
        verify(userProfileRepository).save(argThat(userProfileForSave ->
                userProfileForSave.equals(expectingUserProfile)));
    }

    @Test
    void testEditProfileAsAdmin_Successful() {
        UserProfile userProfileWithEdits = new UserProfile();
        userProfileWithEdits.setFullName("editedTestName");
        userProfileWithEdits.setPhone("editedTestPhone");
        userProfileWithEdits.setEmail("editedTestEmail");
        userProfileWithEdits.setAddress("editedTestAddress");
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        userService.editProfileAsAdmin(userProfileWithEdits);
        verify(userProfileRepository).save(argThat(userProfileForSave ->
                userProfileForSave.getFullName().equals("editedTestName") &&
                        userProfileForSave.getPhone().equals("editedTestPhone") &&
                        userProfileForSave.getEmail().equals("editedTestEmail") &&
                        userProfileForSave.getAddress().equals("editedTestAddress")));
    }

    @Test
    void testEditProfileAsUser_Successful() {
        UserProfile userProfileWithEdits = new UserProfile();
        userProfileWithEdits.setFullName("editedTestName");
        userProfileWithEdits.setPhone("editedTestPhone");
        userProfileWithEdits.setEmail("editedTestEmail");
        userProfileWithEdits.setAddress("editedTestAddress");
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        userService.editProfileAsUser(userProfileWithEdits);
        verify(userProfileRepository).save(argThat(userProfileForSave ->
                userProfileForSave.getFullName().equals("editedTestName") &&
                        userProfileForSave.getPhone().equals("editedTestPhone") &&
                        userProfileForSave.getEmail().equals("editedTestEmail") &&
                        userProfileForSave.getAddress().equals("editedTestAddress")));
    }

    @Test
    void testEditCredentials_Successful() {
        when(passwordEncoder.encode(any())).thenReturn("editedPassword");
        UserCredentials userCredentialsWithEdits = new UserCredentials();
        userCredentialsWithEdits.setUsername("editedUsername");
        userCredentialsWithEdits.setPassword("editedPassword");
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        userService.editCredentials(userCredentialsWithEdits);
        verify(userCredentialsRepository).save(argThat(userCredentialsForSave ->
                userCredentialsForSave.equals(userCredentialsWithEdits)));
    }

    @Test
    void testDelete_Successful() {
        userProfileForTesting.setBucket(new Bucket());
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        userService.delete(1L);
        verify(userProfileRepository).delete(userProfileForTesting);
        verify(bucketRepository).delete(userProfileForTesting.getBucket());
        verify(userCredentialsRepository).delete(userProfileForTesting.getUserCredentials());
    }
}