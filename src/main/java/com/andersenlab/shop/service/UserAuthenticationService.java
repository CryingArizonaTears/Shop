package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;

public interface UserAuthenticationService {
    UserProfileDto getCurrent();

    UserProfileDto getByUsername(String username);

    UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto);
}
