package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;
import by.shop.repository.UserCredentialsRepository;
import by.shop.repository.UserProfileRepository;
import by.shop.service.UserAuthenticationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    UserProfileRepository userProfileRepository;
    UserCredentialsRepository userCredentialsRepository;
    PasswordEncoder passwordEncoder;

    @Logging
    @Override
    public UserProfile getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return getByUsername(currentPrincipalName);
    }

    @Logging
    @Override
    public UserProfile getByUsername(String username) {
        UserCredentials userCredentials = userCredentialsRepository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return userProfileRepository.findById(userCredentials.getId()).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Logging
    @Override
    public UserCredentials getEncryptedUserCredentials(UserCredentials userCredentials) {
        UserCredentials userCredentialsByUsername = getByUsername(userCredentials.getUsername()).getUserCredentials();
        if (!ObjectUtils.isEmpty(userCredentialsByUsername)) {
            if (passwordEncoder.matches(userCredentials.getPassword(), userCredentialsByUsername.getPassword())) {
                return userCredentialsByUsername;
            }
        }
        throw new SecurityException("Incorrect login or password");
    }
}
