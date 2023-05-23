package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.UserCredentialsRepository;
import com.andersenlab.shop.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceAdminImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private BucketRepository bucketRepository;

    @Mock
    private ExtendedModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceAdminImpl userProfileService;

    @Test
    void testCreateUser_Successful() {

        UserProfileDto userProfileDto = new UserProfileDto();
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setPassword("password");
        userProfileDto.setUserCredentialsDto(userCredentialsDto);
        UserProfile userProfile = new UserProfile();
        UserCredentials userCredentials = new UserCredentials();
        Bucket bucket = new Bucket();
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        userProfile.setUserCredentials(userCredentials);
        userProfile.setId(1L);
        when(modelMapper.map(userProfileDto, UserProfile.class)).thenReturn(userProfile);
        when(modelMapper.map(userProfileDto, UserCredentials.class)).thenReturn(userCredentials);
        when(bucketRepository.save(any(Bucket.class))).thenReturn(bucket);
        when(passwordEncoder.encode(userCredentialsDto.getPassword())).thenReturn("encodedPassword");


        userProfileService.create(userProfileDto);


        verify(modelMapper).map(userProfileDto, UserProfile.class);
        verify(modelMapper).map(userProfileDto, UserCredentials.class);
        verify(bucketRepository).save(any(Bucket.class));
        verify(passwordEncoder).encode(userCredentialsDto.getPassword());
        verify(userCredentialsRepository).save(userCredentials);
        verify(userProfileRepository).save(userProfile);
        assertEquals(bucket, userProfile.getBucket());
        assertEquals(1L, userProfile.getId());
        assertEquals("encodedPassword", userCredentials.getPassword());
    }
}