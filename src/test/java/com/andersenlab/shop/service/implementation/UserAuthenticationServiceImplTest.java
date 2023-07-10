package com.andersenlab.shop.service.implementation;

import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.repository.UserCredentialsRepository;
import com.andersenlab.shop.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserAuthenticationServiceImplTest {

    @InjectMocks
    UserAuthenticationServiceImpl userAuthenticationService;
    @Mock
    UserProfileRepository userProfileRepository;
    @Mock
    UserCredentialsRepository userCredentialsRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;
    final UserProfile userProfileForTesting = new UserProfile();
    final UserCredentials userCredentialsForTesting = new UserCredentials();

    @BeforeEach
    void beforeTests() {
        userCredentialsForTesting.setPassword("testPassword");
        userCredentialsForTesting.setUsername("testUsername");
        userProfileForTesting.setUserCredentials(userCredentialsForTesting);
        userProfileForTesting.setPhone("testPhone");
        userProfileForTesting.setAddress("testAddress");
        userProfileForTesting.setEmail("testEmail");
    }

    @Test
    void testGetCurrent_Successful() {
        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName())
                .thenReturn("testUsername");
        when(userCredentialsRepository.getByUsername(any()))
                .thenReturn(Optional.of(userCredentialsForTesting));
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        UserProfile currentUser = userAuthenticationService.getCurrent();
        assertEquals(currentUser, userProfileForTesting);
    }

    @Test
    void testGetByUsername_Successful() {
        when(userCredentialsRepository.getByUsername(any()))
                .thenReturn(Optional.of(userCredentialsForTesting));
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        UserProfile expectingUser = userAuthenticationService.getByUsername("testUsername");
        assertEquals(expectingUser, userProfileForTesting);
    }

    @Test
    void testGetEncryptedUserCredentials_Successful() {
        when(userCredentialsRepository.getByUsername(any()))
                .thenReturn(Optional.of(userCredentialsForTesting));
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);
        UserCredentials expectingUserCredentials = userAuthenticationService.getEncryptedUserCredentials(userCredentialsForTesting);
        assertEquals(expectingUserCredentials, userCredentialsForTesting);
    }

    @Test
    void testGetEncryptedUserCredentials_Unsuccessful() {
        when(userCredentialsRepository.getByUsername(any()))
                .thenReturn(Optional.of(userCredentialsForTesting));
        when(userProfileRepository.findById(any()))
                .thenReturn(Optional.of(userProfileForTesting));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);
        SecurityException expectingException = assertThrows(SecurityException.class,
                () -> userAuthenticationService.getEncryptedUserCredentials(userCredentialsForTesting));
        assertEquals(expectingException.getMessage(), "Incorrect login or password");
    }
}