package com.andersenlab.shop.service;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.UserCredentialsRepository;
import com.andersenlab.shop.repository.UserProfileRepository;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.UserCredentials;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    @Logging
    @Override
    public UserProfileDto getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return getByUsername(currentPrincipalName);
    }

    @Logging
    @Override
    public UserProfileDto getByUsername(String username) {
        UserCredentials userCredentials = userCredentialsRepository.getByUsername(username);
        if (!ObjectUtils.isEmpty(userCredentials)) {
            return modelMapper.map(userProfileRepository.findById(userCredentials.getId()).get(), UserProfileDto.class);
        }
        throw new UsernameNotFoundException("Пользователь не найден");
    }

    @Logging
    @Override
    public UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto) {
        UserCredentialsDto userCredentialsByUsername = getByUsername(userCredentialsDto.getUsername()).getUserCredentialsDto();
        if (!ObjectUtils.isEmpty(userCredentialsByUsername)) {
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), userCredentialsByUsername.getPassword())) {
                return userCredentialsByUsername;
            }
        }
        throw new SecurityException("Неправильный логин или пароль");
    }
}
