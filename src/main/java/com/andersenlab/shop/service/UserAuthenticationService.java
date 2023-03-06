package com.andersenlab.shop.service;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.IUserCredentialsDao;
import com.andersenlab.shop.dao.IUserProfileDao;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.UserCredentials;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    @Autowired
    private IUserProfileDao userProfileDao;
    @Autowired
    private IUserCredentialsDao userCredentialsDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        UserCredentials userCredentials = userCredentialsDao.getByUsername(username);
        if (!ObjectUtils.isEmpty(userCredentials)) {
            return modelMapper.map(userProfileDao.findById(userCredentials.getId()).get(), UserProfileDto.class);
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
