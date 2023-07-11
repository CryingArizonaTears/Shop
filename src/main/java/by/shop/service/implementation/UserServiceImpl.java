package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.Bucket;
import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;
import by.shop.repository.BucketRepository;
import by.shop.repository.UserCredentialsRepository;
import by.shop.repository.UserProfileRepository;
import by.shop.service.RoleService;
import by.shop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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
public class UserServiceImpl implements UserService {

    UserProfileRepository userProfileRepository;
    BucketRepository bucketRepository;
    UserCredentialsRepository userCredentialsRepository;
    PasswordEncoder passwordEncoder;
    RoleService roleService;
    static final String BASED_USER_ROLE_NAME = "ROLE_USER";

    @Logging
    @Override
    public List<UserProfile> getAll() {
        return (List<UserProfile>) userProfileRepository.findAll();
    }

    @Logging
    @Override
    public UserProfile getById(Long id) {
        return userProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Transactional
    @Logging
    @Override
    public UserProfile createAsAdmin(UserProfile userProfile) {
        Bucket bucket = new Bucket();
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketRepository.save(bucket);
        userProfile.getUserCredentials().setPassword(passwordEncoder.encode(userProfile.getUserCredentials().getPassword()));
        userCredentialsRepository.save(userProfile.getUserCredentials());
        userProfile.setBucket(bucket);
        return userProfileRepository.save(userProfile);
    }

    @Transactional
    @Logging
    @Override
    public UserProfile createAsGuest(UserProfile userProfile) {
        userProfile.setId(null);
        userProfile.setRole(roleService.getByName(BASED_USER_ROLE_NAME));
        UserCredentials userCredentials = new UserCredentials();
        Bucket bucket = new Bucket();
        bucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketRepository.save(bucket);
        userCredentials.setId(null);
        userCredentials.setUsername(userProfile.getUserCredentials().getUsername());
        userCredentials.setPassword(passwordEncoder.encode(userProfile.getUserCredentials().getPassword()));
        userCredentialsRepository.save(userCredentials);
        userProfile.setBucket(bucket);
        userProfile.setUserCredentials(userCredentials);
        return userProfileRepository.save(userProfile);
    }

    @Logging
    @Override
    public UserProfile editProfileAsAdmin(UserProfile userProfile) {
        UserProfile userProfileFromRepo = getById(userProfile.getId());
        if (userProfile.getRole() != null) {
            userProfileFromRepo.setRole(userProfile.getRole());
        }
        editProfile(userProfile, userProfileFromRepo);
        return userProfileRepository.save(userProfileFromRepo);
    }

    @Logging
    @Override
    public UserProfile editProfileAsUser(UserProfile userProfile) {
        UserProfile userProfileFromRepo = getById(userProfile.getId());
        editProfile(userProfile, userProfileFromRepo);
        return userProfileRepository.save(userProfileFromRepo);
    }


    @Logging
    @Override
    public UserCredentials editCredentials(UserCredentials userCredentials) {
        UserCredentials userCredentialsFromRepo = getById(userCredentials.getId()).getUserCredentials();
        if (userCredentials.getUsername() != null) {
            userCredentialsFromRepo.setUsername(userCredentials.getUsername());
        }
        if (userCredentials.getPassword() != null) {
            userCredentialsFromRepo.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        }
        return userCredentialsRepository.save(userCredentialsFromRepo);
    }

    @Transactional
    @Logging
    @Override
    public void delete(Long id) {
        UserProfile existingUserProfile = getById(id);
        userProfileRepository.delete(existingUserProfile);
        bucketRepository.delete(existingUserProfile.getBucket());
        userCredentialsRepository.delete(existingUserProfile.getUserCredentials());
    }

    private void editProfile(UserProfile userProfile, UserProfile userProfileFromRepo) {
        if (userProfile.getFullName() != null) {
            userProfileFromRepo.setFullName(userProfile.getFullName());
        }
        if (userProfile.getAddress() != null) {
            userProfileFromRepo.setAddress(userProfile.getAddress());
        }
        if (userProfile.getEmail() != null) {
            userProfileFromRepo.setEmail(userProfile.getEmail());
        }
        if (userProfile.getPhone() != null) {
            userProfileFromRepo.setPhone(userProfile.getPhone());
        }
    }
}
