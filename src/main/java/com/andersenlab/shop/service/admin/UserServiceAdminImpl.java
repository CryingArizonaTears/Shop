package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.UserCredentialsRepository;
import com.andersenlab.shop.repository.UserProfileRepository;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceAdminImpl implements UserService {

    UserProfileRepository userProfileRepository;
    BucketRepository bucketRepository;
    UserCredentialsRepository userCredentialsRepository;
    ExtendedModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    @Logging
    @Override
    public List<UserProfileDto> getAll() {
        List<UserProfile> userProfiles = (List<UserProfile>) userProfileRepository.findAll();
        return modelMapper.mapList(userProfiles, UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto getById(Long id) {
        return modelMapper.map(userProfileRepository.findById(id), UserProfileDto.class);
    }

    @Transactional
    @Logging
    @Override
    public void create(UserProfileDto userProfileDto) {
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        Bucket bucket = new Bucket();
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketRepository.save(bucket);
        userProfile.getUserCredentials().setPassword(passwordEncoder.encode(userProfileDto.getUserCredentialsDto().getPassword()));
        userCredentialsRepository.save(modelMapper.map(userProfileDto, UserCredentials.class));
        userProfile.setBucket(bucket);
        userProfileRepository.save(userProfile);
    }

    @Logging
    @Override
    public void editProfile(UserProfileDto userProfileDto) {
        UserProfileDto userProfile = getById(userProfileDto.getId());
        if (userProfileDto.getRoleDto() != null) {
            userProfile.setRoleDto(userProfileDto.getRoleDto());
        }
        if (userProfileDto.getFullName() != null) {
            userProfile.setFullName(userProfileDto.getFullName());
        }
        if (userProfileDto.getAddress() != null) {
            userProfile.setAddress(userProfileDto.getAddress());
        }
        if (userProfileDto.getEmail() != null) {
            userProfile.setEmail(userProfileDto.getAddress());
        }
        if (userProfileDto.getPhone() != null) {
            userProfile.setPhone(userProfileDto.getPhone());
        }
        userProfileRepository.save(modelMapper.map(userProfile, UserProfile.class));
    }

    @Logging
    @Override
    public void editCredentials(UserCredentialsDto userCredentialsDto) {
        UserCredentialsDto userCredentials = getById(userCredentialsDto.getId()).getUserCredentialsDto();
        if (userCredentialsDto.getUsername() != null) {
            userCredentials.setUsername(userCredentialsDto.getUsername());
        }
        if (userCredentialsDto.getPassword() != null) {
            userCredentials.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword()));
        }
        userCredentialsRepository.save(modelMapper.map(userCredentials, UserCredentials.class));
    }

    @Transactional
    @Logging
    @Override
    public void delete(UserProfileDto userProfileDto) {
        userProfileRepository.delete(modelMapper.map(userProfileDto, UserProfile.class));
        Bucket bucket = new Bucket();
        bucket.setId(userProfileDto.getId());
        bucketRepository.delete(bucket);
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setId(userProfileDto.getId());
        userCredentialsRepository.delete(userCredentials);
    }
}
