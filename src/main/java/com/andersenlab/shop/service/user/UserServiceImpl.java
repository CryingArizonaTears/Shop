package com.andersenlab.shop.service.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.UserCredentialsRepository;
import com.andersenlab.shop.repository.UserProfileRepository;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.service.RoleService;
import com.andersenlab.shop.service.UserAuthenticationService;
import com.andersenlab.shop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    static final String BASED_USER_ROLE_NAME = "ROLE_USER";
    UserProfileRepository userProfileRepository;
    BucketRepository bucketRepository;
    UserCredentialsRepository userCredentialsRepository;
    ModelMapper modelMapper;
    UserAuthenticationService authenticationService;
    RoleService roleService;
    PasswordEncoder passwordEncoder;

    @Logging
    @Override
    public List<UserProfileDto> getAll() {
        return null;
    }

    @Logging
    @Override
    public UserProfileDto getById(Long id) {
        return modelMapper.map(userProfileRepository.findById(authenticationService.getCurrent().getId()).get(), UserProfileDto.class);
    }

    @Transactional
    @Logging
    @Override
    public void create(UserProfileDto userProfileDto) {
        UserProfile userProfile = modelMapper.map(userProfileDto, UserProfile.class);
        userProfile.setId(null);
        userProfile.setRole(modelMapper.map(roleService.getByName(BASED_USER_ROLE_NAME), Role.class));
        UserCredentials userCredentials = new UserCredentials();
        Bucket bucket = new Bucket();
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketRepository.save(bucket);
        userCredentials.setId(null);
        userCredentials.setUsername(userProfileDto.getUserCredentialsDto().getUsername());
        userCredentials.setPassword(passwordEncoder.encode(userProfileDto.getUserCredentialsDto().getPassword()));
        userCredentialsRepository.save(userCredentials);
        userProfile.setBucket(bucket);
        userProfile.setUserCredentials(userCredentials);
        userProfileRepository.save(userProfile);
    }

    @Logging
    @Override
    public void editProfile(UserProfileDto userProfileDto) {
        UserProfileDto userProfile = authenticationService.getCurrent();
        if (userProfileDto.getFullName() != null) {
            userProfile.setFullName(userProfileDto.getFullName());
        }
        if (userProfileDto.getAddress() != null) {
            userProfile.setAddress(userProfileDto.getAddress());
        }
        if (userProfileDto.getEmail() != null) {
            userProfile.setEmail(userProfileDto.getEmail());
        }
        if (userProfileDto.getPhone() != null) {
            userProfile.setPhone(userProfileDto.getPhone());
        }
        userProfileRepository.save(modelMapper.map(userProfile, UserProfile.class));
    }

    @Logging
    @Override
    public void editCredentials(UserCredentialsDto userCredentialsDto) {
        UserCredentialsDto userCredentials = authenticationService.getCurrent().getUserCredentialsDto();
        if (userCredentialsDto.getUsername() != null) {
            userCredentials.setUsername(userCredentialsDto.getUsername());
        }
        if (userCredentialsDto.getPassword() != null) {
            userCredentials.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword()));
        }
        userCredentialsRepository.save(modelMapper.map(userCredentials, UserCredentials.class));
    }

    @Logging
    @Override
    public void delete(UserProfileDto userProfileDto) {

    }
}
